package com.sirin.findartevent.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Message (
    @Json(name ="message")
    val message: String
)