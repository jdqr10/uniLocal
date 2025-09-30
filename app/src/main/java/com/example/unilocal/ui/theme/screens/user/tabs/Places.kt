package com.example.unilocal.ui.theme.screens.user.tabs


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.unilocal.viewmodel.PlacesViewModel
import coil.compose.AsyncImage


@Composable
fun Places(placesViewModel: PlacesViewModel){

    val places by placesViewModel.places.collectAsState()

    LazyColumn {
        items(places){

            Row{
                Text(text = it.title)
            }

            AsyncImage(
                model = it.images[0],
                contentDescription = it.description
            )

        }

    }

}