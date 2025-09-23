package com.example.unilocal.ui.theme.screens.admin.nav.bottombaradmin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unilocal.R
import com.example.unilocal.ui.theme.screens.user.nav.RouteTab

@Composable
fun BottomBarAdmin(
    navController: NavHostController
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar() {

        Destination.entries.forEachIndexed { index, destination ->

            val isSelected = currentDestination?.route == destination.route::class.qualifiedName

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(destination.label)
                    )
                },
                onClick =  {
                    navController.navigate(destination.route)

                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                selected = isSelected
            )

        }


    }
}

enum class Destination(
    val route: RouteTab,
    val label: Int,
    val icon: ImageVector,
){
    CONFIRMATION(RouteTab.Map, R.string.txt_menu_home, Icons.Default.Home),
    PROFILE(RouteTab.Profile, R.string.txt_menu_profile, Icons.Default.AccountCircle)
}