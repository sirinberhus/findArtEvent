package com.sirin.findartevent.ui.screens.Register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.sirin.findartevent.ui.CheckBoxTextField
import com.sirin.findartevent.ui.ClickableLoginTextField
import com.sirin.findartevent.ui.DividerTextField
import com.sirin.findartevent.ui.HeadingTextComponents
import com.sirin.findartevent.ui.MyTextField
import com.sirin.findartevent.ui.NormalTextComponents
import com.sirin.findartevent.ui.PasswordTextField
import com.sirin.findartevent.ui.screens.Routes
import com.sirin.findartevent.ui.theme.Primary

@Composable
fun UserInputScreenRegister(
    navController : NavController,
    viewModel: RegisterViewModel = hiltViewModel()
){
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel,context) {
        viewModel.authResult.collect {result ->
            when (result) {
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
                        "you're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is Result.ApiError -> {
                    val apiResponse: ErrorsResponse? = result.data as ErrorsResponse

                    viewModel.validate(apiResponse)

                    Toast.makeText(
                        context,
                        apiResponse?.message ?: "An error occurred.",
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
        ){
            NormalTextComponents(value = stringResource(id = R.string.hello))
            HeadingTextComponents(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextField(
                value = state.name,
                onValueChange = {
                           viewModel.onEvent(RegisterUiEvent.NameChanged(it))
                },
                labelValue = stringResource(id = R.string.first_name),
                painterResource = painterResource(id = R.drawable.baseline_perm_identity_24),
                errorText = if (state.nameError.isNotBlank()) state.nameError else null
            )

            MyTextField(
                value = state.lastName,
                onValueChange = {
                                viewModel.onEvent(RegisterUiEvent.LastNameChanged(it))
                },
                labelValue = stringResource(id = R.string.last_name),
                painterResource = painterResource(id = R.drawable.baseline_perm_identity_24),
                errorText = if (state.lastNameError.isNotBlank()) state.lastNameError else null
            )

            MyTextField(
                value = state.email,
                onValueChange = {
                                viewModel.onEvent(RegisterUiEvent.EmailChanged(it))

                },
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.baseline_email_24),
                errorText = if (state.emailError.isNotBlank()) state.emailError else null
            )
            PasswordTextField(
                value = state.password,
                onValueChange = {
                                viewModel.onEvent(RegisterUiEvent.PasswordChanged(it))
                },
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.baseline_lock_24),
                errorText = if (state.passwordError.isNotBlank()) state.passwordError else null
            )
            CheckBoxTextField(value = stringResource(id = R.string.condition_and_terms),
                    onTextSelected = {
                         navController.navigate(Routes.TERMS_OF_USE_SCREEN)
                    }
                )
            Spacer(modifier = Modifier.heightIn(60.dp))

            Button(
                onClick = {
                    viewModel.onEvent(RegisterUiEvent.Register)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary ,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    text = "Kayıt ol"
                )
            }

            DividerTextField()
            ClickableLoginTextField( value = stringResource(id = R.string.switch_to_login),
                onTextSelected = {
                   navController.navigate(Routes.USER_INPUT_SCREEN_LOGIN)
            })
        }


    }
}

@Preview
@Composable
fun DefaultPreviewOfUserInputScreenRegister(){
    val navController = rememberNavController()
    UserInputScreenRegister(navController = navController)
}
