package net.luispiressilva.iban.network.models.auth

/**
 * Created by Luis Silva on 30/10/2019.
 *
 * just some dummy class
 */
import com.squareup.moshi.Json

data class AuthenticationResponse(@Json(name = "tokenKey")
                                  val tokenKey: String = "",
                                  @Json(name = "tokenExpiry")
                                  val tokenExpiry: String = "",
                                  @Json(name = "token")
                                  val token: String? = "")