package com.sirin.findartevent.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sirin.findartevent.data.model.Users
import com.sirin.findartevent.ui.screens.Home.HomeScreen
import com.sirin.findartevent.ui.screens.Register.UserInputScreenRegister
import com.sirin.findartevent.ui.screens.login.UserInputScreenLogin

@Composable
fun FindArtEventAppNavigationGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.USER_INPUT_SCREEN_LOGIN){
        composable(Routes.USER_INPUT_SCREEN_LOGIN){
            UserInputScreenLogin(navController)
        }

        composable(Routes.USER_INPUT_SCREEN_REGISTER){
            UserInputScreenRegister(navController)
        }

        composable(
            "Routes.HOME_SCREEN/{user}",
            arguments = listOf(
                navArgument(name = "user") {
                    type = NavType.ParcelableType(Users::class.java)
                }
            )
            ){
            val user = it.arguments?.getParcelable<Users>("user") !!
            HomeScreen(navController, user = user)
        }
        composable(Routes.TERMS_OF_USE_SCREEN){
            TermsOfConditionsScreen()
        }
    }
}