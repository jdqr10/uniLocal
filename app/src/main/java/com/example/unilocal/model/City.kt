package com.example.unilocal.model

enum class City(
    override val displayName: String
): DisplayableEnum {
    DEFAULT("Por defecto"),
    ARMENIA("Armenia"),
    PEREIRA("Pereira"),
    MANIZALES("Manizales"),
    MEDELLIN("Medellin"),
    BOGOTA("Bogotá")

}