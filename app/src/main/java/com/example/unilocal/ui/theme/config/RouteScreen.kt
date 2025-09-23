package com.example.unilocal.ui.theme.config

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

}