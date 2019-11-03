package net.luispiressilva.iban.network.models.response

import com.squareup.moshi.Json
import java.util.*

class ApiError(
        @Json(name = "status") internal val status: Int = 0,
        @Json(name = "title") internal val title: String? = "",
        @Json(name = "details") internal val details: HashMap<String, Any>? = null
)