package com.example.unilocal.model

import java.time.LocalDateTime

class Review(
    val id: String,
    val userID: String,
    val placeID: String,
    val rating: Int,
    val comment: String,
    val date: LocalDateTime
){}