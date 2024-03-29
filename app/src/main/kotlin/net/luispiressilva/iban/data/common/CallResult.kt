package net.luispiressilva.iban.data.common

import net.luispiressilva.iban.network.models.ApiErrorResponse
import okhttp3.Headers

data class CallResult<T>(val status: NetworkState, val code: Int, val data: T?, val message: String?, val headers: Headers? = null, val connection: DataBoundResource<T, *, *>? = null) {

    companion object {
        fun <T> success(code: Int, data: T?, headers: Headers?, connection: DataBoundResource<T, *, *>? = null) = CallResult(NetworkState.SUCCESS, code, data, null, headers, connection)

        fun <T> error(message: String?, code: Int, data: T?, error: ApiErrorResponse<*>?, connection: DataBoundResource<T, *, *>? = null) = CallResult(NetworkState.error(error), code, data, message, null, connection)

        fun <T> error(message: String?, code: Int, data: String?, connection: DataBoundResource<T, *, *>? = null) = CallResult(NetworkState.error(null), code, null as T?, data, null, connection)

        fun <T> loading(data: T? = null, code: Int = 0, connection: DataBoundResource<T, *, *>? = null) = CallResult(NetworkState.LOADING, code, data, null, null, connection)

        fun <T> empty(code: Int, message: String?, data: T? = null, connection: DataBoundResource<T, *, *>? = null) = CallResult(NetworkState.EMPTY, code, data, message, null, connection)
    }

}