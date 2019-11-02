package net.luispiressilva.iban.data.common.pagination

import net.luispiressilva.iban.network.models.ApiErrorResponse

class NetworkState(
        val status: Int,
        val error: ApiErrorResponse<*>? = null
) {
    val isLoading: Boolean
        get() = status == Status.LOADING

    val isSuccess: Boolean
        get() = status == Status.SUCCESS

    val isFailed: Boolean
        get() = status == Status.FAILED

    val isEmpty: Boolean
        get() = status == Status.EMPTY

    val isEnd: Boolean
        get() = status == Status.NO_MORE

    val statusString: String
        get() {
            return when (status) {
                Status.LOADING -> "running"
                Status.SUCCESS -> "success"
                Status.EMPTY -> "empty"
                Status.NO_MORE -> "no more"
                else -> "failed"
            }
        }

    companion object {
        val SUCCESS = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.LOADING)
        val EMPTY = NetworkState(Status.EMPTY)
        val NO_MORE = NetworkState(Status.NO_MORE)
        fun error(error: ApiErrorResponse<*>?) = NetworkState(Status.FAILED, error)
    }
}

object Status {
    const val LOADING = 0
    const val SUCCESS = 1
    const val FAILED = 2
    const val EMPTY = 3
    const val NO_MORE = 4
}