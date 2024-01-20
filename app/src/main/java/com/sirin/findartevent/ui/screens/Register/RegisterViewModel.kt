package com.sirin.findartevent.ui.screens.Register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirin.findartevent.data.model.ErrorsResponse
import com.sirin.findartevent.data.repository.AuthRepository
import com.sirin.findartevent.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(RegisterState())

    private val resultChannel = Channel<Result<out Any?>> ()
    val authResult = resultChannel.receiveAsFlow()

    init {
        authenticatedUser()
    }

    fun onEvent(event: RegisterUiEvent) {
        when(event) {
            is RegisterUiEvent.NameChanged -> {
                state = state.copy(name = event.value)
            }
            is RegisterUiEvent.LastNameChanged -> {
                state = state.copy(lastName = event.value)
            }
            is RegisterUiEvent.EmailChanged -> {
                state = state.copy(email = event.value)
            }
            is RegisterUiEvent.PasswordChanged -> {
                state = state.copy(password = event.value)
            }
            is RegisterUiEvent.NameError -> {
                state = state.copy(nameError = event.value)
            }
            is RegisterUiEvent.LastNameError -> {
                state = state.copy(lastNameError = event.value)
            }
            is RegisterUiEvent.EmailError -> {
                state = state.copy(emailError = event.value)
            }
            is RegisterUiEvent.PasswordError -> {
                state = state.copy(passwordError = event.value)
            }

            is RegisterUiEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = repository.register(
                name = state.name,
                lastName = state.lastName,
                email = state.email,
                password = state.password,
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
                "name" -> {
                    state = state.copy(nameError = error.value[0])
                }
                "lastName" -> {
                    state = state.copy(lastNameError = error.value[0])
                }
                "email" -> {
                    state = state.copy(emailError = error.value[0])
                }
                "password" -> {
                    state = state.copy(passwordError = error.value[0])
                }
            }
        }
    }

}