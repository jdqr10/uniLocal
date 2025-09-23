package com.example.unilocal.ui.theme.screens.admin.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.unilocal.ui.theme.screens.admin.tabs.Confirmation
import com.example.unilocal.ui.theme.screens.admin.tabs.ProfileAdmin
import androidx.navigation.compose.composable
import com.example.unilocal.ui.theme.screens.admin.nav.RouteTabAdmin

@Composable
fun ContentAdmin(
    padding: PaddingValues,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = RouteTabAdmin.Confirmation,
        modifier = Modifier.padding(padding)
    ){
        composable<RouteTabAdmin.Confirmation>{
            Confirmation()
        }
        composable<RouteTabAdmin.ProfileAdmin>{
            ProfileAdmin()
        }
    }

}