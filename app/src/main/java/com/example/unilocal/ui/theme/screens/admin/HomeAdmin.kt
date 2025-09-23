package com.example.unilocal.ui.theme.screens.admin

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
import com.example.unilocal.ui.theme.screens.admin.nav.ContentAdmin
import com.example.unilocal.ui.theme.screens.admin.nav.bottombaradmin.BottomBarAdmin

@Composable
fun HomeAdmin(){
    val navController = rememberNavController()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarAdmin()
        },
        bottomBar = {
            BottomBarAdmin(
                navController = navController
            )
        }
    ){padding ->
        ContentAdmin(
            navController = navController,
            padding = padding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAdmin(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_admin)
            )
        }
    )
}