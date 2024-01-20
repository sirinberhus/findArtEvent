package com.sirin.findartevent.data.model

data class RegisterRequest (
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)