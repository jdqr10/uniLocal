package com.example.unilocal.ui.theme.screens.user.tabs

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.unilocal.ui.theme.components.Stars
import com.example.unilocal.viewmodel.PlacesViewModel

@Composable
fun PlaceDetail(
    placesViewModel: PlacesViewModel,
    id: String,
){
//    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val place = placesViewModel.findById(id)
    val images = place?.images ?: emptyList()

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp),
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
                color = Color.DarkGray,  // puedes ajustar el color
                thickness = 1.dp,         // grosor de la línea
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // separación arriba/abajo
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
    }
}