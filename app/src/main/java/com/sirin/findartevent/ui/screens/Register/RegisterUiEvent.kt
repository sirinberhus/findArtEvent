package com.sirin.findartevent.ui.screens.Register

sealed class RegisterUiEvent {
    data class NameChanged(val value: String): RegisterUiEvent()
    data class LastNameChanged(val value: String): RegisterUiEvent()
    data class EmailChanged(val value: String): RegisterUiEvent()
    data class PasswordChanged(val value: String): RegisterUiEvent()
    data class NameError(val value: String): RegisterUiEvent()
    data class LastNameError(val value: String): RegisterUiEvent()
    data class EmailError(val value: String): RegisterUiEvent()
    data class PasswordError(val value: String): RegisterUiEvent()

    object Register: RegisterUiEvent()
}