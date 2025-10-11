package com.example.unilocal.ui.theme.screens.user.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.unilocal.ui.theme.screens.user.tabs.Map
import com.example.unilocal.ui.theme.screens.user.tabs.NewPlace
import com.example.unilocal.ui.theme.screens.user.tabs.PlaceDetail
import com.example.unilocal.ui.theme.screens.user.tabs.Places
import com.example.unilocal.ui.theme.screens.user.tabs.Profile
import com.example.unilocal.ui.theme.screens.user.tabs.Search
import com.example.unilocal.viewmodel.PlacesViewModel


@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController
){

    val placesViewModel : PlacesViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = RouteTab.Map,
        modifier = Modifier.padding(padding)
    ){
        composable<RouteTab.Map>{
            Map()
        }

        composable<RouteTab.NewPlace>{
            NewPlace()
        }

        composable<RouteTab.Search>{
            Search(
                placesViewModel = placesViewModel,
                onNavigateToPlaceDetail = { id ->
                    navController.navigate(RouteTab.PlaceDetail(id))
                }
            )
        }
        composable<RouteTab.Places>{
            Places(
                placesViewModel = placesViewModel,
                onNavigateToPlaceDetail = {
                    navController.navigate(RouteTab.PlaceDetail(it))
                }
            )
        }
        composable<RouteTab.Profile>{
            Profile()
        }
        composable<RouteTab.PlaceDetail>{
            val args = it.toRoute<RouteTab.PlaceDetail>()
            PlaceDetail(
                placesViewModel = placesViewModel,
                id = args.id
            )
        }


    }

}