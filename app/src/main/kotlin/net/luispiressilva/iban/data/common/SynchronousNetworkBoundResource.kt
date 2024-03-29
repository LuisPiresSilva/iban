package net.luispiressilva.iban.data.common

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import net.luispiressilva.iban.app.API_TIMEOUT
import net.luispiressilva.iban.network.models.ApiErrorResponse
import net.luispiressilva.iban.network.models.ApiResponse
import net.luispiressilva.iban.network.models.ApiSuccessResponse

abstract class SynchronousNetworkBoundResource<ResultType, RefreshType>
@MainThread constructor() {

    private var refreshCall: ApiResponse<RefreshType>? = null

    fun initCall(): CallResult<ResultType> {
        return requestFromNetwork()
    }

    private fun requestFromNetwork(): CallResult<ResultType> {
        val apiResponse = createCall()!!

        when (apiResponse) {
            is ApiSuccessResponse -> {
                val responseBody = processResponse(apiResponse)
                saveCallResult(responseBody)
                publishEvent(responseBody)

                return CallResult.success(200, responseBody, apiResponse.headers)

            }
            is ApiErrorResponse -> {
//                if (apiResponse.errorCode == 401) {
//                    return if (accountUtils.refreshingToken.get()) {
//                        var i = API_TIMEOUT //MUST BE IN SECONDS
//                        if (i > 100) {
//                            throw Exception("API_TIMEOUT must be in seconds and less than 100")
//                        }
//                        while (accountUtils.refreshingToken.get() && i > 0) {
//                            Thread.sleep(1000)
//                            i--
//                        }
//                        requestFromNetwork()
//                    } else {
//                        accountUtils.refreshingToken.set(true)
//                        refresh(refreshCall)
//                    }
//                } else {
                    val responseBody = processResponse(apiResponse)
                    return CallResult.error(apiResponse.errorMessage, apiResponse.errorCode, null, apiResponse)
//                }
            }
        }
    }

    @MainThread
    protected open fun refreshCall(): ApiResponse<RefreshType>? = null

    @MainThread
    protected open fun refreshResult(apiResponse: RefreshType?) { }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<ResultType>) = response.body

    @WorkerThread
    protected open fun processResponse(response: ApiErrorResponse<ResultType>) = response.errorMessage

    @WorkerThread
    protected open fun saveCallResult(data: ResultType?) { }

    @WorkerThread
    protected open fun publishEvent(data: ResultType?) { }

    @MainThread
    protected open fun createCall(): ApiResponse<ResultType>? = null

    @MainThread
    protected open fun createCall(obj: Any): ApiResponse<ResultType>? = null

    private fun refresh(response: ApiResponse<RefreshType>?): CallResult<ResultType> {
        val refreshResponse = refreshCall()

//        accountUtils.refreshingToken.set(false)

        return when (refreshResponse) {
            is ApiSuccessResponse -> {
                refreshResult(refreshResponse.body)
                requestFromNetwork()
            }
            is ApiErrorResponse -> CallResult.error(refreshResponse.errorMessage, refreshResponse.errorCode, null, refreshResponse)
            else -> CallResult.error("", -1, "")
        }
    }

}