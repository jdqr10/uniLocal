package com.example.unilocal.ui.theme.screens.user.nav

import kotlinx.serialization.Serializable

sealed class RouteTab{

    @Serializable
    data object Map : RouteTab()

    @Serializable
    data object NewPlace : RouteTab()

    @Serializable
    data object Search : RouteTab()

    @Serializable
    data object Places : RouteTab()

    @Serializable
    data object Profile : RouteTab()


}