package com.example.unilocal.ui.theme.screens.user.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.unilocal.R

@Composable
fun Map(){
    Image(
        painter = painterResource(id = R.drawable.map),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.fillMaxSize()
    )
}