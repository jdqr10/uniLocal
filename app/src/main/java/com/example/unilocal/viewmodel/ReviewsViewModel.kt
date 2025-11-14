package com.example.unilocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.Review
import com.example.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ReviewsViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _reviewResult = MutableStateFlow<RequestResult?>(null)
    val reviewResult: StateFlow<RequestResult?> = _reviewResult.asStateFlow()

    /**
     * Cargar reseñas del lugar desde Firestore.
     */
    fun loadReviewsForPlace(placeId: String) {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("places")
                    .document(placeId)
                    .collection("reviews")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .get()
                    .await()

                val list = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Review::class.java)?.apply {
                        this.id = doc.id
                        if (this.placeID.isBlank()) {
                            this.placeID = placeId
                        }
                    }
                }

                _reviews.value = list
                _reviewResult.value = null

            } catch (e: Exception) {
                _reviews.value = emptyList()
                _reviewResult.value =
                    RequestResult.Failure(e.message ?: "Error al cargar reseñas")
            }
        }
    }

    /**
     * Crear reseña para un lugar.
     */
    fun create(review: Review) {
        viewModelScope.launch {
            _reviewResult.value = RequestResult.Loading

            try {
                val placeId = review.placeID
                if (placeId.isBlank()) {
                    _reviewResult.value =
                        RequestResult.Failure("El lugar de la reseña no es válido")
                    return@launch
                }

                // Guardar reseña en subcolección del lugar
                val docRef = db.collection("places")
                    .document(placeId)
                    .collection("reviews")
                    .add(review)
                    .await()

                // Actualizamos el ID en memoria
                review.id = docRef.id

                // Añadimos a la lista local
                _reviews.value = _reviews.value + review

                _reviewResult.value = RequestResult.Success("Reseña creada con éxito")

            } catch (e: Exception) {
                _reviewResult.value =
                    RequestResult.Failure(e.message ?: "Error al crear la reseña")
            }
        }
    }

    fun resetResult() {
        _reviewResult.value = null
    }
}
