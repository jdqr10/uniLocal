package com.example.unilocal.model

data class User(
    var id: String = "",
    val name: String = "",
    val username: String = "",
    val role: Role = Role.USER,
    val city: City = City.ARMENIA,
    val email: String = "",
    val password: String = "",
)