package com.sirin.findartevent.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Errors(
    @Json(name="email")
    val email: List<String>,
    @Json(name="password")
    val password: List<String>
)