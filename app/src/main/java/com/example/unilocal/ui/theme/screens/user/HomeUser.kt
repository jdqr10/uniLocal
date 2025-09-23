package com.example.unilocal.ui.theme.screens.user


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.R
import com.example.unilocal.ui.theme.screens.user.nav.ContentUser
import com.example.unilocal.ui.theme.screens.user.nav.bottombar.BottomBaUser


@Composable
fun HomeUser(){

    val navController = rememberNavController()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBaUser()
        },
        bottomBar = {
            BottomBaUser(
                navController = navController
            )
        }
    ){padding ->
        ContentUser(
            navController = navController,
            padding = padding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBaUser(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_usere)
            )
        }
    )
}

