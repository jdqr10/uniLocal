package com.example.unilocal.ui.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R

@Composable
fun Stars(
    number: Int
){

    Row {
        (0..4).forEach {
            Icon(
                modifier = Modifier.width(15.dp),
                imageVector = Icons.Default.Star,
                contentDescription = "Star $it",
                tint = if (it < number) {
                    colorResource(R.color.star)
                } else { Color.Gray }
            )
        }
    }

}