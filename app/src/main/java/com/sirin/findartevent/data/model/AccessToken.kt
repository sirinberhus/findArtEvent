package com.sirin.findartevent.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class AccessToken(
    @Json(name="access_token")
    val accessToken: String,
    @Json(name = "token_type")
    val tokenType: String
)