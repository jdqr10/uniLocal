package com.example.unilocal.ui.theme.screens.admin.nav

import kotlinx.serialization.Serializable

sealed class RouteTabAdmin {

    @Serializable
    data object Confirmation : RouteTabAdmin()

    @Serializable
    data object ProfileAdmin : RouteTabAdmin()
}