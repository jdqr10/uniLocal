package com.example.unilocal.ui.theme.screens.user.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.unilocal.model.Place
import com.example.unilocal.ui.theme.screens.LocalMainViewModel
import com.example.unilocal.viewmodel.PlacesViewModel

/* ====== UI model para la tarjeta grande ====== */
data class UiPlaceCard(
    val id: String,
    val title: String,
    val address: String,
    val thumbnail: String,
    val description: String,
    val rating: Double,        // quemado por ahora
    val reviews: Int,          // quemado por ahora
    val distanceText: String   // quemado por ahora
)

/* ====== Pantalla con SearchBar + Lista ====== */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    onNavigateToPlaceDetail: (String) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val placesViewModel = LocalMainViewModel.current.placesViewModel
    placesViewModel.getAll()

    val places by placesViewModel.places.collectAsState()

    // Filtrado en vivo (case-insensitive). Si query vacío -> todos
    val filtered: List<Place> by remember(query, places) {
        mutableStateOf(
            places
                .filter { it.status == Place.STATUS_AUTHORIZED }
                .let { authorized ->
                    if (query.isBlank()) authorized
                    else {
                        val q = query.trim().lowercase()
                        authorized.filter { p ->
                            p.title.lowercase().contains(q) ||
                                    p.address.lowercase().contains(q) ||
                                    p.type.name.lowercase().contains(q) ||
                                    p.city.name.lowercase().contains(q)
                        }
                    }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                active = false
                focusManager.clearFocus()
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Buscar lugares…") }
        ) {
            /* Sugerencias opcionales */
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = filtered, key = { it.id }) { p ->

                // Mapeo de Place -> UiPlaceCard (valores quemados por ahora)
                val ui = UiPlaceCard(
                    id = p.id,
                    title = p.title,
                    address = p.address,
                    thumbnail = p.images.firstOrNull().orEmpty(),
                    description = p.description,
                    rating = 4.8,                  // <- quemado
                    reviews = 500,                 // <- quemado
                    distanceText = "1,2 millas"    // <- quemado (usa coma como en tu mock)
                )

                PlaceLargeCard(
                    place = ui,
                    indicators = p.images.size.coerceAtLeast(1),
                    onClick = { onNavigateToPlaceDetail(p.id) }
                )
            }

            if (filtered.isEmpty()) {
                item {
                    Text(
                        text = "Sin resultados para “$query”.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

/* ====== Tarjeta grande estilo mock ====== */
@Composable
private fun PlaceLargeCard(
    place: UiPlaceCard,
    indicators: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                model = place.thumbnail,
                contentDescription = place.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Indicadores tipo carrusel (centrados abajo)
            if (indicators > 0) {
                DotsIndicator(
                    count = indicators,
                    selectedIndex = 0, // si luego haces pager, pásalo real
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = place.title,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(8.dp))

        // ⭐ rating (reseñas)   📍 distancia
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "☆ ${place.rating} (${place.reviews} reseñas)",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Outlined.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = place.distanceText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        Spacer(Modifier.height(12.dp))

        // Botón “Seleccionar” naranja (pill)
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF7A44), // naranja similar al mock
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Seleccionar",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

/* ====== Indicadores redondos ====== */
@Composable
private fun DotsIndicator(
    count: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 6.dp,
    dotSpacing: Dp = 8.dp,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(count) { idx ->
            val alpha = if (idx == selectedIndex) 1f else 0.35f
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = alpha))
            )
            if (idx != count - 1) Spacer(Modifier.width(dotSpacing))
        }
    }
}
