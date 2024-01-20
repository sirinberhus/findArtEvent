package com.sirin.findartevent.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirin.findartevent.data.model.ErrorsResponse
import com.sirin.findartevent.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.sirin.findartevent.data.utils.Result


@HiltViewModel
class LoginViewModel
    @Inject constructor(
        private val repository: AuthRepository
    ) : ViewModel() {
    var state by mutableStateOf(LoginState())

    private val resultChannel = Channel<Result<out Any>>()
    val authResult = resultChannel.receiveAsFlow()

    init {
        authenticatedUser()
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                state = state.copy(email = event.value)
            }

            is LoginUiEvent.PasswordChanged -> {
                state = state.copy(password = event.value)
            }

            is LoginUiEvent.EmailError -> {
                state = state.copy(emailError = event.value)
            }

            is LoginUiEvent.PasswordError -> {
                state = state.copy(passwordError = event.value)
            }

            is LoginUiEvent.Login -> {
                login()
            }

        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)


            val result: Result<out Any> = repository.login(
                email = state.email,
                password = state.password
            )

            resultChannel.send(result)

            state = state.copy(isLoading = false)

        }
    }

    private fun authenticatedUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = repository.getCurrentUser()

            resultChannel.send(result)

            state = state.copy(isLoading = false)
        }
    }

    fun validate(apiResponse: ErrorsResponse?) {
        apiResponse?.errors?.forEach {error ->
            when(error.key) {
                "email" -> {
                    state.copy(emailError = error.value[0])
                }

                "password" -> {
                    state.copy(passwordError = error.value[0])
                }
            }
        }
    }
}
