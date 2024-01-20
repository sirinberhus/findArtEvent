package com.sirin.findartevent.ui.screens.Register

data class RegisterState (
    val isLoading: Boolean = false,
    val name: String = "",
    val nameError: String = "",
    val lastName: String = "",
    val lastNameError: String = "",
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = ""
)
