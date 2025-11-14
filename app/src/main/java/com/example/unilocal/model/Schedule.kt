package com.example.unilocal.model

import java.time.DayOfWeek
import java.time.LocalTime
import java.util.Date

class Schedule(
    val day: DayOfWeek = DayOfWeek.MONDAY,
    val open: Date = Date(),
    val close: Date = Date()
) {
    fun toDisplayString(): String {
        return "${day.name} $open - $close"
    }
}
