package com.example.unilocal.ui.theme.screens.user.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.unilocal.model.PlaceType
import com.example.unilocal.viewmodel.PlacesViewModel

@Composable
fun HomeUserContent(
    placesViewModel: PlacesViewModel,
    onOpenSearch: () -> Unit,
    onNavigateToPlaceDetail: (String) -> Unit,
    onCreatePlace: () -> Unit = {}
) {
    val places by placesViewModel.places.collectAsState()
    var selectedType: PlaceType? by remember { mutableStateOf(null) }

    val recommended = remember(places, selectedType) {
        val base = if (selectedType == null) places else places.filter { it.type == selectedType }
        base.take(10) // muestra hasta 10
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp)
    ) {
        // 1) Entrada rápida a búsqueda
        SearchEntry(onOpenSearch = onOpenSearch)

        Spacer(Modifier.height(12.dp))

        // 2) Chips de categorías
        CategoryChips(
            selected = selectedType,
            onSelect = { selectedType = if (it == selectedType) null else it }
        )

        Spacer(Modifier.height(16.dp))

        // 3) Carrusel de recomendados / cerca de ti
        Text(
            text = if (selectedType == null) "Recomendados" else "Resultados: ${selectedType?.name?.lowercase()?.replaceFirstChar { c -> c.titlecase() }}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(8.dp))

        if (recommended.isEmpty()) {
            Text(
                text = "No hay lugares para mostrar.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(recommended, key = { it.id }) { p ->
                    PlaceWideCard(
                        place = p,
                        onClick = { onNavigateToPlaceDetail(p.id) }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // 4) CTA crear lugar
        Button(
            onClick = onCreatePlace,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF7A44),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear un lugar")
        }
    }
}

/* ======= Composables de UI ======= */

@Composable
private fun SearchEntry(onOpenSearch: () -> Unit) {
    // Caja que parece un SearchBar pero navega a tu pantalla de búsqueda
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable { onOpenSearch() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Buscar lugares…",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CategoryChips(
    selected: PlaceType?,
    onSelect: (PlaceType?) -> Unit
) {
    val scroll = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scroll),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Chip "Todos"
        FilterChip(
            label = { Text("Todos") },
            selected = selected == null,
            onClick = { onSelect(null) }
        )
        // Chips por PlaceType
        PlaceType.entries.forEach { type ->
            FilterChip(
                label = { Text(type.name.lowercase().replaceFirstChar { it.titlecase() }) },
                selected = selected == type,
                onClick = { onSelect(type) }
            )
        }
    }
}

@Composable
private fun FilterChip(
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Box(Modifier.padding(horizontal = 14.dp, vertical = 8.dp)) {
            label()
        }
    }
}

@Composable
private fun PlaceWideCard(
    place: Place,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = place.images.firstOrNull().orEmpty(),
            contentDescription = place.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = place.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = place.address,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
