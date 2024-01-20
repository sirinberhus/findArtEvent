package com.sirin.findartevent.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sirin.findartevent.R
import com.sirin.findartevent.data.model.ErrorsResponse
import com.sirin.findartevent.data.model.Users
import com.sirin.findartevent.data.utils.Result
import com.sirin.findartevent.ui.ButtonRegister
import com.sirin.findartevent.ui.ClickableLoginScreenTextField
import com.sirin.findartevent.ui.DividerTextField
import com.sirin.findartevent.ui.HeadingTextComponents
import com.sirin.findartevent.ui.MyTextField
import com.sirin.findartevent.ui.NormalTextComponents
import com.sirin.findartevent.ui.PasswordTextField
import com.sirin.findartevent.ui.UnderLineTextComponents
import com.sirin.findartevent.ui.screens.Routes

@Composable
fun UserInputScreenLogin(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
    ){
        val state = viewModel.state
        val context = LocalContext.current

        LaunchedEffect(viewModel, context) {
            viewModel.authResult.collect {result ->
                when(result) {
                    is Result.Authorized -> {
                        val user = result.data as Users
                        navController.navigate(
                            "${Routes.HOME_SCREEN}/${user}"
                        ) {
                            popUpTo(Routes.USER_INPUT_SCREEN_LOGIN) {
                                inclusive = true
                            }
                        }
                    }

                    is Result.Unauthorized -> {
                        Toast.makeText(
                            context,
                            "You are not authorized",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is Result.ApiError -> {
                        val apiResponse: ErrorsResponse? = result.data as ErrorsResponse?

                        viewModel.validate(apiResponse)

                        Toast.makeText(
                            context,
                            apiResponse?.message ?: "An error occurred",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NormalTextComponents(value = stringResource(id = R.string.hello))
            HeadingTextComponents(value = stringResource(id = R.string.welcome_back))
            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                value = state.email ,
                onValueChange = { it ->
                    viewModel.onEvent(LoginUiEvent.EmailChanged(it))
                },
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.baseline_email_24),
                errorText = if (state.emailError.isNotBlank()) state.emailError else null
            )


            PasswordTextField(
                value = state.password,
                onValueChange = {newValue ->
                    viewModel.onEvent(LoginUiEvent.PasswordChanged(newValue))
                },
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.baseline_lock_24),
                errorText = if (state.passwordError.isNotBlank()) state.passwordError else null
            )
            Spacer(modifier = Modifier.height(40.dp))
            UnderLineTextComponents(value = stringResource(id = R.string.forgot_passwprd))
            Spacer(modifier = Modifier.height(40.dp))
            ButtonRegister(stringResource(id = R.string.login_button))
            DividerTextField()
            ClickableLoginScreenTextField(value = stringResource(id =R.string.dont_have_accaount ),
                onTextSelected = {
                    navController.navigate(Routes.USER_INPUT_SCREEN_REGISTER)
                } )
        }
    }
}

@Preview
@Composable
fun DefaultUserInputScreenLoginPreview(){
    val navController = rememberNavController()
    UserInputScreenLogin(navController = navController)
}