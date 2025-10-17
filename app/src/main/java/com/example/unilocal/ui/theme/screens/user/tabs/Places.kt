package com.example.unilocal.ui.theme.screens.user.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.unilocal.model.Place
import com.example.unilocal.ui.theme.screens.LocalMainViewModel
import com.example.unilocal.viewmodel.PlacesViewModel

// Estado sin hacer 'quemado'
enum class PlaceStatus { APROBADO, PENDIENTE }

data class UiPlace(
    val id: String,
    val title: String,
    val address: String,
    val status: PlaceStatus,
    val thumbnail: String,
    val description: String = ""
)

@Composable
fun Places(
    onNavigateToPlaceDetail: (String) -> Unit
) {
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val places by placesViewModel.places.collectAsState()


    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = places, key = { it.id }) { p: Place ->


            val ui = UiPlace(
                id = p.id,
                title = p.title,
                address = p.address,
                status = PlaceStatus.PENDIENTE,
                thumbnail = p.images.firstOrNull().orEmpty(),
                description = p.description
            )

            PlaceCard(
                place = ui,
                onClick = { onNavigateToPlaceDetail(p.id) }
            )
        }
    }
}

@Composable
private fun PlaceCard(
    place: UiPlace,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen izquierda
            AsyncImage(
                model = place.thumbnail,
                contentDescription = place.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 110.dp, height = 90.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = place.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF0E3A6D),
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = null,
                        tint = Color(0xFF808993),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF808993)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(Modifier.width(8.dp))

            // Derecha: chip de estado (placeholder)
            StatusChip(status = place.status)
        }
    }
}

@Composable
private fun StatusChip(status: PlaceStatus) {
    val (bg, label) = when (status) {
        PlaceStatus.APROBADO -> Color(0xFF2E7D32) to "APROBADO"
        PlaceStatus.PENDIENTE -> Color(0xFFEF6C00) to "PENDIENTE"
    }
    Box(
        modifier = Modifier
            .background(bg, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}
