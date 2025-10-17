package com.example.unilocal.ui.theme.screens.user.nav.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unilocal.R
import com.example.unilocal.ui.theme.screens.user.nav.RouteTab

@Composable
fun BottomBaUser(
    navController: NavHostController,
    showTopBar: (Boolean) -> Unit,
    showFAB: (Boolean) -> Unit,
    titleTopBar: (Int) -> Unit
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(currentDestination) {
        val destination = Destination.entries.find { it.route::class.qualifiedName == currentDestination?.route }
        if (destination != null) {
            showTopBar(destination.showTopBar)
            showFAB(destination.showFAB)
            titleTopBar(destination.label)
        }
    }

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
    val showTopBar: Boolean = true,
    val showFAB: Boolean = false
){
    HOME(RouteTab.Map, R.string.txt_menu_home, Icons.Default.Home, showTopBar = false),
    SERACH(RouteTab.Search, R.string.txt_menu_search, Icons.Default.Search, showTopBar = false),
    //NEW_PLACE(RouteTab.NewPlace, R.string.txt_menu_new_place, Icons.Default.AddCircle),
    PLACES(RouteTab.Places, R.string.txt_menu_places, Icons.Default.Place, showFAB = true),
    PROFILE(RouteTab.Profile, R.string.txt_menu_profile, Icons.Default.AccountCircle)
}