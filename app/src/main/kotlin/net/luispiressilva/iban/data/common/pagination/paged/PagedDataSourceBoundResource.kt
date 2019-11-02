package net.luispiressilva.iban.data.common.pagination.paged

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PageKeyedDataSource
import net.luispiressilva.iban.data.common.CallResult
import net.luispiressilva.iban.data.common.ServerErrors
import net.luispiressilva.iban.data.common.Status
import net.luispiressilva.iban.data.common.pagination.NetworkState
import net.luispiressilva.iban.network.models.ApiErrorResponse
import net.luispiressilva.iban.network.models.ApiResponse
import net.luispiressilva.iban.utils.helper.AppExecutors
import retrofit2.Response

abstract class PagedDataSourceBoundResource<PaginationType, ResultType>(
    private val appExecutors: AppExecutors,
    private val usePaging: Boolean
) : PageKeyedDataSource<Int, ResultType>() {

    var retry: (() -> Any)? = null
    var loadnext: (() -> Any)? = null
    val networkData = MediatorLiveData<MutableList<ResultType>>()
    val network = MediatorLiveData<NetworkState>()
    val initial = MediatorLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ResultType>) {
        loadInit(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultType>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultType>) {
        loadNext(params, callback)
    }


    fun loadInit(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ResultType>?) {
        postInitialState(NetworkState.LOADING)
        appExecutors.getNetworkIO().execute {
            val currentPage = initialPage()
            val nextPage = currentPage + 1


            val apiResponse = createCall(currentPage, params.requestedLoadSize)


//        appExecutors.getMainThread().execute {
//            network.addSource(apiResponse) { response ->
//                network.removeSource(apiResponse)
            when (apiResponse.status.status) {
                Status.SUCCESS -> {

                    val responseBody = processResponse(apiResponse)
                    retry = null
                    val nextKey = calculateNextKey(responseBody!!, nextPage)

                    if (usePaging) {
                        appExecutors.getMainThread().execute {
                            callback?.onResult(identifyResponseList(responseBody), null, nextKey)
                        }
                    } else {
                        loadnext = nextKey?.let { { loadNext(LoadParams<Int>(nextKey, params.requestedLoadSize), null) } }
//                        appExecutors.getMainThread().execute {
                        postInitialState(identifyResponseList(responseBody), null, nextKey)
                        if (nextKey == null) {
                            postInitialState(NetworkState.NO_MORE)
                        } else {
                            postInitialState(NetworkState.SUCCESS)
                        }
//                        }
                    }

                }
                Status.FAILED -> {
//                    appExecutors.getMainThread().execute {
                    retry = { loadInit(params, callback) }
                    postInitialState(
                        NetworkState.error(
                            apiResponse.status.error
                        ))
//                    }
                }
                else -> {
                }
            }
//            }
        }
    }


    private fun loadNext(params: LoadParams<Int>, callback: LoadCallback<Int, ResultType>?) {
        postAfterState(NetworkState.LOADING)
        appExecutors.getNetworkIO().execute {
            val currentPage = params.key
            val nextPage = currentPage + 1

            val apiResponse = createCall(currentPage, params.requestedLoadSize)

//        appExecutors.getMainThread().execute {
//            network.addSource(apiResponse) { response ->
//                network.removeSource(apiResponse)

            when (apiResponse.status.status) {
                Status.SUCCESS -> {
//                appExecutors.getDiskIO().execute {
                    val responseBody = processResponse(apiResponse)
                    retry = null
                    val nextKey = calculateNextKey(responseBody!!, nextPage)

                    if (usePaging) {
                        appExecutors.getMainThread().execute {
                            callback?.onResult(identifyResponseList(responseBody), nextKey)
                        }
                    } else {
                        loadnext = nextKey?.let { { loadNext(LoadParams(nextKey, params.requestedLoadSize), null) } }
//                        appExecutors.getMainThread().execute {
                        postAfterState(identifyResponseList(responseBody), nextKey)
                        if (nextKey == null) {
                            postAfterState(NetworkState.NO_MORE)
                        } else {
                            postAfterState(NetworkState.SUCCESS)
                        }
//                        }
                    }
                }
//            }
                Status.FAILED -> {
//                    appExecutors.getMainThread().execute {
                    retry = { loadNext(params, callback) }
                    postInitialState(
                        NetworkState.error(
                            apiResponse.status.error
                        ))
//                    }
                }
                else -> {
                }
            }
        }
//        }
    }


    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { retry ->
            appExecutors.getNetworkIO().execute { retry() }
        }
    }

    //LIST LISTING
    private fun postInitialState(content: List<ResultType>, previousKey: Int?, nextKey: Int?) {

        val list: MutableList<ResultType> = mutableListOf()
        list.addAll(content)
        appExecutors.getMainThread().execute {
            networkData.value = list
        }
    }

    private fun postAfterState(content: List<ResultType>, nextKey: Int?) {

        val list = networkData.value
        list?.addAll(content)
        appExecutors.getMainThread().execute {
            networkData.value = list
        }
    }

    open fun loadNext() {
        loadnext?.invoke()
    }

    open fun listListingInvalidate() {
        appExecutors.getMainThread().execute {
            networkData.value = mutableListOf()
            networkData.postValue(networkData.value)
        }
        loadInit(LoadInitialParams(initialPage(), false), null)
    }


    //PAGED LISTING
    private fun postInitialState(state: NetworkState) {
        appExecutors.getMainThread().execute {
            network.value = state
            initial.value = state
        }
    }

    private fun postAfterState(state: NetworkState) {
        appExecutors.getMainThread().execute {
            network.value = state
        }
    }

    @WorkerThread
    protected open fun processResponse(response: CallResult<PaginationType>) = response.data

    @MainThread
    protected abstract fun createCall(page: Int, pageSize: Int): CallResult<PaginationType>

    protected abstract fun calculateNextKey(response: PaginationType, nextPage: Int): Int?

    protected abstract fun identifyResponseList(response: PaginationType): List<ResultType>

    protected abstract fun initialPage(): Int
}
