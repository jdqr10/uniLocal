package com.example.unilocal.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.unilocal.ui.theme.config.RouteScreen
import com.example.unilocal.ui.theme.screens.admin.HomeAdmin
import com.example.unilocal.ui.theme.screens.user.HomeUser

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Welcome//RouteScreen.HomeU //RouteScreen.HomeU//RouteScreen.Welcome
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