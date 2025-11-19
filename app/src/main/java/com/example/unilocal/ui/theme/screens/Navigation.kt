package com.example.unilocal.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.unilocal.ui.theme.config.RouteScreen
import com.example.unilocal.ui.theme.screens.admin.HomeAdmin
import com.example.unilocal.ui.theme.screens.places.CreatePlaceScreen
import com.example.unilocal.ui.theme.screens.places.PlaceDetail
import com.example.unilocal.ui.theme.screens.user.HomeUser
import com.example.unilocal.utils.SharedPrefsUtil
import com.example.unilocal.viewmodel.MainViewModel
import com.example.unilocal.model.Role
import com.example.unilocal.viewmodel.UsersViewModel


val LocalMainViewModel = staticCompositionLocalOf<MainViewModel> { error("MainViewModel not found") }

@Composable
fun Navigation(
    mainViewModel: MainViewModel
){

    val context = LocalContext.current
    val navController = rememberNavController()
    var user by remember { mutableStateOf( SharedPrefsUtil.getPreferences(context)) }

    val startDestination = if(user.isEmpty()){
        RouteScreen.Login
    }else{
        if (user["role"] == "ADMIN"){
            RouteScreen.HomeA
        }else{
            RouteScreen.HomeU
        }
    }


    CompositionLocalProvider(
        LocalMainViewModel provides mainViewModel
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination //RouteScreen.Login//RouteScreen.HomeU //RouteScreen.HomeU//RouteScreen.Welcome
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
                    onNavigateToHome = { userId, role ->

                        SharedPrefsUtil.savePreferences(context, userId, role)
                        user = SharedPrefsUtil.getPreferences(context)

                        if (role == Role.ADMIN){
                            navController.navigate(RouteScreen.HomeA)
                        }else{
                            navController.navigate(RouteScreen.HomeU)
                        }
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
                HomeUser(
                    userId = user["userId"]!!,
                    onNavigateToCreatePlace = {
                        navController.navigate(RouteScreen.CreatePlace)
                    },
                    onNavigateToPlaceDetail = {
                        navController.navigate(RouteScreen.PlaceDetail(it))
                    },
                    logout = {
                        SharedPrefsUtil.clearPreferences(context)
                        mainViewModel.placesViewModel.resetMyPlaces()
                        navController.navigate(RouteScreen.Login)
                    }
                )
            }

            composable<RouteScreen.HomeA>{
                HomeAdmin(
                    logout = {
                        SharedPrefsUtil.clearPreferences(context)
                        navController.navigate(RouteScreen.Login)
                    }
                )
            }

            composable<RouteScreen.PlaceDetail>{
                val args = it.toRoute<RouteScreen.PlaceDetail>()
                PlaceDetail(
                    userId = user["userId"],
                    placeId = args.id
                )
            }

            composable<RouteScreen.CreatePlace> {
                CreatePlaceScreen(
                    userId = user["userId"],
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

        }

    }

}