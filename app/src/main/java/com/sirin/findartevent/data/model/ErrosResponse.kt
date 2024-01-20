package com.sirin.findartevent.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class ErrorsResponse(
    @Json(name="errors")
    var errors: Map<String, List<String>>? = null,
    @Json(name="message")
    var message: String? = null
)