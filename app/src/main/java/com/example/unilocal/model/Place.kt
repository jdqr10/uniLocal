package com.example.unilocal.model

import java.time.LocalDateTime
import kotlin.toString

class Place(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val address: String = "",
    val city: City = City.DEFAULT,
    val location: Location = Location(),
    val images: List<String> = emptyList<String>(),
    val phoneNumber: String = "",
    val type: PlaceType = PlaceType.DEFAULT,
    val schedules: List<Schedule> = emptyList<Schedule>(),
    val ownerId: String = ""
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

        return true  //now.toLocalTime().isAfter(schedule.open) && now.toLocalTime().isBefore(schedule.close)
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