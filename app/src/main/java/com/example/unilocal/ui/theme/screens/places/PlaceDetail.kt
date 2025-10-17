package com.example.unilocal.ui.theme.screens.places

import StatusChip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    userId: String?,
    placeId: String,
){

    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val reviewsViewModel = LocalMainViewModel.current.reviewsViewModel

    val reviews = remember { mutableStateListOf<Review>() }
    reviews.addAll( reviewsViewModel.getReviewsByPlace(placeId) )

    val place = placesViewModel.findById(placeId)
    val images = place?.images ?: emptyList()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showComments by remember { mutableStateOf(false) }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showComments = true
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Comment,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                        model = images.first(),
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


            Text(
                text = place?.description ?: "",
            )


            Divider(
                color = Color.DarkGray,  // puedes ajustar el color
                thickness = 1.dp,         // grosor de la línea
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // separación arriba/abajo
            )

            Row {
                Text(
                    text = "Horario: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "8:00 AM - 8:00 PM",
                )
            }

            Text(
                text = "3435356546",
            )

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

            Text(
                text = place?.description ?: "",
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(10.dp)
            ) {

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

        if(showComments){
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showComments = false
                }
            ){

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Comentarios",
                    )
                    CommentsList(
                        reviews = reviews
                    )
                    CreateCommentForm(
                        placeId = placeId,
                        userId = userId,
                        onCreateReview = {
                            reviews.add(it)
                            reviewsViewModel.create(it)
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
){
    LazyColumn {
        items(reviews){
            ListItem(
                headlineContent = {
                    Text(text = it.username)
                },
                supportingContent = {
                    Text(text = it.comment)
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
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
){

    var comment by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Escribe un comentario"
                )
            }
        )
        IconButton(
            onClick = {

                val review = Review(
                    id = UUID.randomUUID().toString(),
                    userID = userId ?: "",
                    username = "User",
                    placeID = placeId,
                    rating = 5,
                    comment = comment,
                    date = LocalDateTime.now()
                )

                if(comment.isEmpty() ){
                    return@IconButton
                }

                onCreateReview(review)
                comment = ""
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Send,
                contentDescription = null
            )
        }
    }
}