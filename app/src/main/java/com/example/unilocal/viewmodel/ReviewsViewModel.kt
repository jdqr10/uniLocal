package com.example.unilocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.Review
import com.example.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
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

    private val userNameCache = mutableMapOf<String, String>()

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

                val userNames = fetchUserNames(list.map { it.userID }.toSet())
                list.forEach { review ->
                    val name = userNames[review.userID]
                    if (!name.isNullOrBlank()) {
                        review.username = name
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

                val userName = fetchUserNames(setOf(review.userID))[review.userID]
                if (!userName.isNullOrBlank()) {
                    review.username = userName
                }

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

    private suspend fun fetchUserNames(userIds: Set<String>): Map<String, String> {
        if (userIds.isEmpty()) {
            return emptyMap()
        }

        val result = mutableMapOf<String, String>()
        val idsToFetch = userIds.filter { it.isNotBlank() && !userNameCache.containsKey(it) }

        idsToFetch.chunked(10).forEach { batch ->
            val querySnapshot = db.collection("users")
                .whereIn(FieldPath.documentId(), batch)
                .get()
                .await()

            querySnapshot.documents.forEach { doc ->
                val name = doc.getString("name") ?: doc.getString("username") ?: ""
                userNameCache[doc.id] = name
            }
        }

        userIds.forEach { id ->
            userNameCache[id]?.let { cachedName ->
                result[id] = cachedName
            }
        }

        return result
    }

}
