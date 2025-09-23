package com.example.unilocal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unilocal.model.Location
import com.example.unilocal.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unilocal.model.Place
import com.example.unilocal.model.PlaceType


class PlacesViewModel: ViewModel() {

    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        _places.value = listOf(
            Place(
                id = "1",
                title = "Restaurante El paisa",
                description = "Descripción del restaurante 1",
                address = "Cra 12 #12 - 12",
                location = Location(1.0, 2.0),
                images = listOf("https://elbalconpaisa.com/images/about-img-1.png"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.RESTAURANTE,
                schedules = listOf()
            ),
            Place(
                id = "2",
                title = "Caturro",
                description = "CATURROOO TEST",
                address = "Cra 12 #12 - 12",
                location = Location(1.0, 2.0),
                images = listOf("https://lh3.googleusercontent.com/p/AF1QipPkxI4q-E6KVzrCayGPMgtkrWG7TYTq4fkwjeUU=s1360-w1360-h1020"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.CAFETERIA,
                schedules = listOf()
            )
        )

    }

    fun create(place: Place){
        _places.value = _places.value + place
    }

    fun findById(id: String): Place?{
        return _places.value.find { it.id == id }
    }

    fun findByType(type: PlaceType): List<Place>{
        return _places.value.filter { it.type == type }
    }

    fun findByLocation(location: Location): List<Place>{
        return _places.value.filter { it.location == location }
    }

    fun findByName(name: String): List<Place>{
        return _places.value.filter { it.title.contains(name) }
    }
}