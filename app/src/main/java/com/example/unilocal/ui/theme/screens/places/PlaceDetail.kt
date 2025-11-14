package com.example.unilocal.ui.theme.screens.places

import StatusChip
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
//import androidx.compose.material3.icons.Icons.Default
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.unilocal.model.Review
import com.example.unilocal.ui.theme.components.Stars
import com.example.unilocal.ui.theme.screens.LocalMainViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    userId: String?,
    placeId: String,
) {
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val reviewsViewModel = LocalMainViewModel.current.reviewsViewModel
    val usersViewModel = LocalMainViewModel.current.usersViewModel


    //  Cargar datos desde Firebase
    LaunchedEffect(placeId) {
        placesViewModel.loadPlaceById(placeId)
        reviewsViewModel.loadReviewsForPlace(placeId)
    }


    val place by placesViewModel.currentPlace.collectAsState()
    val reviews by reviewsViewModel.reviews.collectAsState()


    val images = place?.images ?: emptyList()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showComments by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showComments = true },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Ver comentarios"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(13.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Imagen principal (usa la primera si existe)
            AsyncImage(
                model = images.firstOrNull().orEmpty(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
            )

            Text(
                text = place?.title ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusChip(isOpen = true)
                Stars(4)
            }

            Text(text = place?.description ?: "")

            Divider(
                color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row {
                Text(
                    text = "Horario: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = "8:00 AM - 8:00 PM")
            }

            Text(text = "3435356546")

            Divider(
                color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Descripción:",
                fontWeight = FontWeight.Bold
            )

            Text(text = place?.description ?: "")

            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(10.dp)
            ) {

                if (images.isNotEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        AsyncImage(
                            model = images.first(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(250.dp)
                                .clip(RoundedCornerShape(15.dp))
                        )
                    }

                    items(images.drop(1)) { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(15.dp))
                        )
                    }
                }
            }
        }

        if (showComments) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showComments = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Comentarios",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    CommentsList(reviews = reviews)

                    CreateCommentForm(
                        placeId = placeId,
                        userId = userId,
                        onCreateReview = { review ->
                            reviewsViewModel.create(review)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CommentsList(
    reviews: List<Review>
) {
    LazyColumn {
        items(reviews) { review ->
            ListItem(
                headlineContent = { Text(text = review.username) },
                supportingContent = { Text(text = review.comment) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
fun CreateCommentForm(
    placeId: String,
    userId: String?,
    onCreateReview: (Review) -> Unit
) {
    var comment by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "Escribe un comentario") }
        )

        IconButton(
            onClick = {
                if (comment.isBlank()) {
                    return@IconButton
                }

                val review = Review(
                    id = UUID.randomUUID().toString(),
                    userID = userId ?: "",
                    username = "User", // luego puedes usar el nombre real
                    placeID = placeId,
                    rating = 5, // luego puedes conectar con tus estrellas
                    comment = comment,
                    date = com.google.firebase.Timestamp.now()
                )

                onCreateReview(review)
                comment = ""
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Enviar comentario"
            )
        }
    }
}
