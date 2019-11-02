package net.luispiressilva.iban.network

import androidx.lifecycle.LiveData
import net.luispiressilva.iban.data.common.LiveDataWrapper
import net.luispiressilva.iban.network.models.ApiResponse
import net.luispiressilva.iban.network.models.gist.PublicGist
import retrofit2.Call
import retrofit2.http.*

/**
 * Collection of endpoints from api that not need token to be executed.
 */
interface EndpointCollection {


    @GET("users/{username}/gists")
    fun fetchUserPublicGists(@Path("username") username: String): LiveData<ApiResponse<List<PublicGist>>>

}
