package net.luispiressilva.iban.data.common

import com.google.gson.Gson
import net.luispiressilva.iban.BuildConfig
import net.luispiressilva.iban.app.TIMEOUT_ERROR_CODE
import okhttp3.Headers
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

@Suppress("unused")
sealed class ApiResponse<T> {

    companion object {
        fun <T> create(url: String, throwable: Throwable? = null): ApiErrorResponse<T> {
            if(BuildConfig.DEBUG) throwable?.printStackTrace()

            if(throwable is IOException){
                return ApiErrorResponse(url, TIMEOUT_ERROR_CODE, throwable.message ?: "Timeout")
            }
            return ApiErrorResponse(url, 520, throwable?.message ?: "Something went wrong")
        }

        fun <T> create(url: String, response: Response<T>): ApiResponse<T> {
            return (if (response.isSuccessful) {
                val body = response.body()

                //TODO - remove this piece of code if server returns error codes propely
                //just return 'ApiSuccessResponse(url, body, response.headers())' the only sucess scenario

                val reader = JSONObject(Gson().toJson(response.body()))
                val success = reader.getBoolean("succeeded")


                if(success) {
                    ApiSuccessResponse(url, body, response.headers())
    //                    ApiErrorResponse(url, 401, "test")
                } else {
                    var message = "Something went wrong"
                    if(reader.has("message")) {
                        message = reader.getJSONArray("message")[0].toString()
                    }

                    Timber.i("intercept - %s", message)
                    return ApiErrorResponse(url, response.code(), message)
                }

            } else {
                processError(url, response)
            })
        }

        private fun<T> processError(url: String, response: Response<T>) : ApiResponse<T> {
            val code = response.code()
            val message = response.errorBody()?.string()
            val errorMessage = if (message.isNullOrEmpty()) {
                response.message()
            } else {
                message
            }
            return ApiErrorResponse(url, code, errorMessage ?: "Something went wrong")
        }
    }

}

data class ApiSuccessResponse<T>(val url: String, val body: T?, val headers: Headers) : ApiResponse<T>()

data class ApiErrorResponse<T>(val url: String, val errorCode: Int, val errorMessage: String) : ApiResponse<T>()