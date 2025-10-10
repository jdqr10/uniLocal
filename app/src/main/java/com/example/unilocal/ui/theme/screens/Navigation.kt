package com.example.unilocal.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.unilocal.ui.theme.config.RouteScreen
import com.example.unilocal.ui.theme.screens.admin.HomeAdmin
import com.example.unilocal.ui.theme.screens.user.HomeUser
import com.example.unilocal.viewmodel.UsersViewModel

@Composable
fun Navigation(){

    val navController = rememberNavController()
    val userViewModel: UsersViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.HomeU//RouteScreen.HomeU //RouteScreen.HomeU//RouteScreen.Welcome
    ){
        composable<RouteScreen.Welcome>{
            WelcomScreen(
                onNavigateToLogin = {
                    navController.navigate(RouteScreen.Login)
                }
            )
        }

        composable<RouteScreen.Login>{
            LoginScreen(
                usersViewModel = userViewModel,
                onNavigateToRegister = {
                    navController.navigate(RouteScreen.Register)
                },
                onNavigateToHome = {
                    navController.navigate(RouteScreen.HomeU)
                }
            )
        }

        composable<RouteScreen.Register>{
            RegisterScreen(
                usersViewModel = userViewModel,
                onNavigateToLogin = {
                    navController.navigate(RouteScreen.Login)
                }
            )
        }

        composable<RouteScreen.HomeU>{
            HomeUser()
        }

        composable<RouteScreen.HomeA>{
            HomeAdmin()
        }

    }

}