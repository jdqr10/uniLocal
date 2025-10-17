package com.example.unilocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unilocal.ui.theme.UniLocalTheme
import com.example.unilocal.ui.theme.screens.Navigation
import com.example.unilocal.viewmodel.MainViewModel
import com.example.unilocal.viewmodel.PlacesViewModel
import com.example.unilocal.viewmodel.ReviewsViewModel
import com.example.unilocal.viewmodel.UsersViewModel


class MainActivity : ComponentActivity() {

    private val usersViewModel : UsersViewModel by viewModels()
    private val placesViewModel : PlacesViewModel by viewModels()
    private val reviewsViewModel : ReviewsViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainViewModel = MainViewModel(
            placesViewModel,
            usersViewModel,
            reviewsViewModel
        )

        setContent {
            UniLocalTheme {
                Navigation(
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UniLocalTheme {
        Greeting("Android")
    }
}