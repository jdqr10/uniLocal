package com.example.unilocal.model

import com.google.firebase.Timestamp
import java.time.LocalDateTime

class Review(
    var id: String = "",
    var userID: String = "",
    var username: String = "",
    var placeID: String = "",
    var rating: Int = 0,
    var comment: String = "",
    var date: Timestamp = Timestamp.now()
){}