package com.example.unilocal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unilocal.model.City
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
                location = Location(4.5380, -75.6725), // Armenia
                images = listOf("https://elbalconpaisa.com/images/about-img-1.png"),
                phoneNumber = "3123123123",
                type = PlaceType.RESTAURANT,
                city = City.MANIZALES,
                schedules = listOf(),
                ownerId = "2"
            ),
            Place(
                id = "2",
                title = "Caturro",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
                address = "Cra 12 #12 - 12",
                location = Location(4.5372, -75.6664), // Armenia
                images = listOf("https://lh3.googleusercontent.com/p/AF1QipPkxI4q-E6KVzrCayGPMgtkrWG7TYTq4fkwjeUU=s1360-w1360-h1020"),
                phoneNumber = "3123123123",
                type = PlaceType.COFFE,
                city = City.ARMENIA,
                schedules = listOf(),
                ownerId = "2"
            ),
            Place(
                id = "3",
                title = "Hotel de prueba",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(4.8143, -75.6946), // Pereira
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phoneNumber = "3123123123",
                type = PlaceType.HOTEL,
                city = City.PEREIRA,
                schedules = listOf(),
                ownerId = "2"
            ),
            Place(
                id = "4",
                title = "Shopping test 1",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(6.2088, -75.5705), // Medellín
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phoneNumber = "3123123123",
                type = PlaceType.SHOPPING,
                city = City.MEDELLIN,
                schedules = listOf(),
                ownerId = "3"
            ),
            Place(
                id = "5",
                title = "Shopping test 2",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(4.7171, -74.0460), // Bogotá norte
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phoneNumber = "3123123123",
                type = PlaceType.SHOPPING,
                city = City.BOGOTA,
                schedules = listOf(),
                ownerId = "3"
            ),
            Place(
                id = "6",
                title = "Parque de prueba",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(4.6486, -74.0566), // Parque Nacional, Bogotá
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phoneNumber = "3123123123",
                type = PlaceType.PARK,
                city = City.BOGOTA,
                schedules = listOf(),
                ownerId = "3"
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