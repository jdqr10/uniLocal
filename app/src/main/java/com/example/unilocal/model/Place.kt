package com.example.unilocal.model

import java.time.LocalDateTime
import kotlin.toString

class Place(
    val id: String,
    val title: String,
    val description: String,
    val address: String,
    val city: City,
    val location: Location,
    val images: List<String>,
    val phones: List<String>,
    val type: PlaceType,
    val schedules: List<Schedule>,
){

    fun isOpen():Boolean{
        val now = LocalDateTime.now()

        if(schedules.isEmpty()){
            return false
        }

        val schedule = schedules.find { it.day == now.dayOfWeek }

        if(schedule == null){
            return false
        }

        return now.toLocalTime().isAfter(schedule.open) && now.toLocalTime().isBefore(schedule.close)
    }

    fun hourClosed(): String{
        val now = LocalDateTime.now()

        if(schedules.isEmpty()){
            return ""
        }

        val schedule = schedules.find { it.day == now.dayOfWeek }

        if(schedule == null){
            return ""
        }

        return schedule.close.toString()
    }


}