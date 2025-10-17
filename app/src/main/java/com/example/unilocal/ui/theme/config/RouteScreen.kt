package com.example.unilocal.ui.theme.config

import com.example.unilocal.ui.theme.screens.user.nav.RouteTab
import kotlinx.serialization.Serializable

sealed class RouteScreen{

    @Serializable
    data object Welcome : RouteScreen()

    @Serializable
    data object HomeU : RouteScreen()

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object Register : RouteScreen()

    @Serializable
    data object HomeA : RouteScreen()

    @Serializable
    data class PlaceDetail(val id: String) : RouteScreen()

    @Serializable
    data object CreatePlace : RouteScreen()

}