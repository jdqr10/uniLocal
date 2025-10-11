package com.example.unilocal.model

import java.time.DayOfWeek
import java.time.LocalTime

class Schedule(
    val day: DayOfWeek,
    val open: LocalTime,
    val close: LocalTime
) {
    fun toDisplayString(): String {
        return "${day.name} $open - $close"
    }
}
