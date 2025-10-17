package com.example.unilocal.ui.theme.screens.user

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.unilocal.R
import com.example.unilocal.ui.theme.screens.user.nav.ContentUser
import com.example.unilocal.ui.theme.screens.user.nav.bottombar.BottomBaUser

@Composable
fun HomeUser(
    logout: () -> Unit,
    onNavigateToCreatePlace: () -> Unit,
    onNavigateToPlaceDetail: (String) -> Unit
) {
    val navController = rememberNavController()

    var showFAB by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(true) }
    var titleRes by remember { mutableIntStateOf(R.string.title_user) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                TopBaUser(
                    titleRes = titleRes,
                    logout = logout
                    )
            }
        },
        floatingActionButton = {
            if (showFAB) {
                FloatingActionButton(onClick = onNavigateToCreatePlace) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add"
                    )
                }
            }
        },
        bottomBar = {
            BottomBaUser(
                navController = navController,
                showTopBar = { showTopBar = it },
                showFAB = { showFAB = it },
                titleTopBar = { newTitleRes ->
                    titleRes = newTitleRes // ← asignas tu estado, no android.R.attr.title
                }
            )
        }
    ) { padding ->
        ContentUser(
            navController = navController,
            padding = padding,
            onNavigateToPlaceDetail = onNavigateToPlaceDetail
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBaUser(
    @StringRes titleRes: Int,
    logout: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = titleRes)) // ← usa el @StringRes recibido
        },actions = {
            IconButton(
                onClick = {
                    logout()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null
                )
            }
        },
    )
}
