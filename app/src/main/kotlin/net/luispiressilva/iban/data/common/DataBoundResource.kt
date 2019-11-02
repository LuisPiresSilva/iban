package net.luispiressilva.iban.data.common

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import net.luispiressilva.iban.app.API_TIMEOUT
import net.luispiressilva.iban.network.models.ApiErrorResponse
import net.luispiressilva.iban.network.models.ApiResponse
import net.luispiressilva.iban.network.models.ApiSuccessResponse
import net.luispiressilva.iban.utils.helper.AppExecutors

// DB -> Network -> Auth
abstract class DataBoundResource<ResultType, RequestType, RefreshType> @MainThread constructor(
    private val appExecutors: AppExecutors
) {

    enum class Type { NETWORK, DATABASE, BOTH }

    private val result = LiveDataWrapper<ResultType>()
    private var network = MediatorLiveData<RequestType>()
    private val refresh = MediatorLiveData<CallResult<RefreshType>>()

    private var refreshCall: LiveData<ApiResponse<RefreshType>>? = null
    private var requestType: Type = Type.NETWORK

    private val refreshObserver: Observer<ApiResponse<RefreshType>> by lazy {
        RefreshObserver()
    }

    init {
        setValue(CallResult.loading(connection = this))
    }

    fun network() {
        requestType = Type.NETWORK
        result.removeSource(network)
        network = MediatorLiveData()
        requestFromNetwork()
    }

    fun database() {
        requestType = Type.DATABASE
        val databaseSource = loadFromDatabase()
        if (databaseSource != null) {
            result.removeSource(network)
            result.addSource(databaseSource) { newData ->
                setValue(CallResult.success(200, newData, null, this))
            }
        }
    }

    //UNDER DEVELOPEMENT
    //trying to mix into single observer both calls ie, we listen to data change in the db
    // but still can make network calls from the same returned connection, retry them
    // and handle responses
    fun dbAndNetwork() {
        requestType = Type.BOTH
        val databaseSource = loadFromDatabase()
        if (databaseSource != null) {
            result.addSource(databaseSource) { data ->
                setValue(CallResult.success(0, data, null, this))
                if (shouldRequestFromNetwork(data)) {
                    result.removeSource(databaseSource)
                    result.removeSource(network)
                    requestFromNetwork(true)
                }
            }
        }
    }

    fun retry() {
        when (requestType) {
            Type.NETWORK -> network()
            Type.DATABASE -> database()
            Type.BOTH -> dbAndNetwork()
        }
    }

    private fun requestFromNetwork(withDb: Boolean = false) {
        val apiResponse = createCall()!!

        setValue(CallResult.loading())

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.getDiskIO().execute {
                        val responseBody = processResponse(response)
                        saveCallResult(responseBody)
                        val databaseSource = loadFromDatabase()
                        if (withDb && databaseSource != null) {
                            appExecutors.getMainThread().execute {
                                setValue(CallResult.success(200, null, response.headers, this))
                                //must improve this part -> unnecessary second callback
                                result.removeSource(databaseSource)
                                result.addSource(databaseSource) { newData ->
                                    setValue(CallResult.success(0, newData, null, this))
                                }
                            }
                        } else {
                            appExecutors.getMainThread().execute {
                                result.addSource(network) { newData ->
                                    setValue(CallResult.success(200, newData as ResultType, response.headers, this))
                                }
                                updateNetworkSource(responseBody)
                            }
                        }
                    }
                }
                //must handle BOTH scenario for error and refresh token scenarios
                is ApiErrorResponse -> {
                    appExecutors.getMainThread().execute {
                        refreshCall = refreshCall()

                        //handles automatic refresh token, commented out for this exercise only

//                        if (response.errorCode == 401 && refreshCall != null) {
//                            if (accountUtils.refreshingToken.get()) {
//                                appExecutors.getNetworkIO().execute {
//                                    var i = API_TIMEOUT //MUST BE IN SECONDS
//                                    if (i > 100) {
//                                        throw Exception("API_TIMEOUT must be in seconds and less than 100")
//                                    }
//                                    while (accountUtils.refreshingToken.get() && i > 0) {
//                                        Thread.sleep(1000)
//                                        i--
//                                    }
//                                    appExecutors.getMainThread().execute {
//                                        requestFromNetwork()
//                                    }
//                                }
//                            } else {
//                                accountUtils.refreshingToken.set(true)
//                                refresh.addSource(refreshCall!!) {
//                                    refresh.removeSource(refreshCall!!)
//                                }
//                                refreshCall?.observeForever(refreshObserver)
//                            }
//                        } else {
                            result.addSource(network) { newData ->
                                setValue(CallResult.error(response.errorMessage, response.errorCode, newData as ResultType, response, this))
                            }

                            updateNetworkSource(null)
//                        }
                    }
                }
            }
        }
    }

    fun asLiveData() = result

    @MainThread
    private fun setValue(newValue: CallResult<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @MainThread
    private fun updateNetworkSource(newValue: RequestType?) {
        network.value = newValue
    }

    @MainThread
    protected open fun refreshCall(): LiveData<ApiResponse<RefreshType>>? = null

    @MainThread
    protected open fun refreshResult(apiResponse: RefreshType?) { }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected open fun saveCallResult(data: RequestType?) { }

    @WorkerThread
    protected open fun publishEvent(data: ResultType?) {}

    @MainThread
    open fun loadFromDatabase(): LiveData<ResultType>? = null

    @MainThread
    protected open fun shouldRequestFromNetwork(data: ResultType?) = true

    @MainThread
    protected open fun createCall(): LiveData<ApiResponse<RequestType>>? = null

    private inner class RefreshObserver : Observer<ApiResponse<RefreshType>> {
        override fun onChanged(response: ApiResponse<RefreshType>?) {
//            accountUtils.refreshingToken.set(false)
            if (response is ApiSuccessResponse) {
                refreshResult(response.body)
                retry()
            }

            refreshCall?.removeObserver(this)
        }
    }

}
