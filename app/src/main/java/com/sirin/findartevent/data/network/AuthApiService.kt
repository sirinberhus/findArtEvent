package com.sirin.findartevent.data.network

import com.sirin.findartevent.data.model.AccessToken
import com.sirin.findartevent.data.model.LoginRequest
import com.sirin.findartevent.data.model.Message
import com.sirin.findartevent.data.model.RegisterRequest
import com.sirin.findartevent.data.model.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    suspend fun login (
        @Body request: LoginRequest
    ): Response<AccessToken>

    @POST("register")
    suspend fun register (
        @Body request: RegisterRequest
    ): Response<AccessToken>

    @GET("users")
    suspend fun users (
        @Header("Authorization") token: String
    ): Response<Users>

    @POST("logout")
    suspend fun logout (
        @Header("Authorization") token: String
    ): Response<Message>
}