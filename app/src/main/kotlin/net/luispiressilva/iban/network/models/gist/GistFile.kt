package net.luispiressilva.iban.network.models.gist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Luis Silva on 01/11/2019.
 */
@JsonClass(generateAdapter = true)
data class GistFile (
    @Json(name = "filename")
    val filename: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "language")
    val language: String?,
    @Json(name = "raw_url")
    val raw_url: String?,
    @Json(name = "size")
    val size: Long = 0
)