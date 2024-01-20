package com.sirin.findartevent.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    @Json(name = "email")
    val email: String,
    @Json(name ="last_name")
    val lastName: String,
    @Json(name ="id")
    val id: Int,

    @Json(name ="name")
    val name: String
) : Parcelable