package com.sirin.findartevent.data.repository

import com.sirin.findartevent.data.utils.Result

interface AuthRepository {
    suspend fun login (
        email: String,
        password: String
    ): Result<out Any>

    suspend fun register (
        name: String,
        lastName: String,
        email: String,
        password: String
    ): Result<out Any>

    suspend fun getCurrentUser (): Result<out Any>
    suspend fun logout (): Result<out Any>
}