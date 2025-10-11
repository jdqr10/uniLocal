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
                location = Location(1.0, 2.0),
                images = listOf("https://elbalconpaisa.com/images/about-img-1.png"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.RESTAURANTE,
                city = City.MANIZALES,
                schedules = listOf()
            ),
            Place(
                id = "2",
                title = "Caturro",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
                address = "Cra 12 #12 - 12",
                location = Location(1.0, 2.0),
                images = listOf("https://lh3.googleusercontent.com/p/AF1QipPkxI4q-E6KVzrCayGPMgtkrWG7TYTq4fkwjeUU=s1360-w1360-h1020"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.CAFETERIA,
                city = City.ARMENIA,
                schedules = listOf()
            ),
            Place(
                id = "3",
                title = "Hotel de prueba",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.HOTEL,
                city = City.PEREIRA,
                schedules = listOf()
            ),
            Place(
                id = "4",
                title = "Shopping test 1",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.SHOPPING,
                city = City.MEDELLIN,
                schedules = listOf()
            ),
            Place(
                id = "5",
                title = "Shopping test 2",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.SHOPPING,
                city = City.BOGOTA,
                schedules = listOf()
            ),
            Place(
                id = "6",
                title = "Parque de prueba",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.PARK,
                city = City.BOGOTA,
                schedules = listOf()
            ),
            Place(
                id = "7",
                title = "Parque de prueba",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
                phones = listOf("123456789", "987654321"),
                type = PlaceType.PARK,
                city = City.BOGOTA,
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