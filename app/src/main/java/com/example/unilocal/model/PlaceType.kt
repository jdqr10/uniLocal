package com.example.unilocal.model

enum class PlaceType(
    override val displayName: String
): DisplayableEnum {
    DEFAULT("Por defecto"),
    RESTAURANT("Restaurante"),
    BAR("Bar"),
    HOTEL("Hotel"),
    PARK("Parque"),
    COFFE("Cafeteria"),
    SHOPPING("Tienda"),
    OTHER("Otro")


}