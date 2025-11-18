package com.example.unilocal.ui.theme.screens.user.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unilocal.R
import com.example.unilocal.model.City
import com.example.unilocal.ui.theme.AppColors
import com.example.unilocal.ui.theme.components.InputText
import com.example.unilocal.ui.theme.components.OperationResultHandler
import com.example.unilocal.ui.theme.screens.LocalMainViewModel
import com.example.unilocal.utils.RequestResult

@Composable
fun Profile(
    userId: String
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val currentUser by usersViewModel.currentUser.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var selectedCityIndex by rememberSaveable { mutableIntStateOf(City.DEFAULT.ordinal) }

    val selectedCity = City.entries.getOrElse(selectedCityIndex) { City.DEFAULT }

    LaunchedEffect(userId) {
        if (currentUser?.id != userId) {
            usersViewModel.findById(userId)
        }
    }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            name = it.name
            username = it.username
            email = it.email
            selectedCityIndex = it.city.ordinal
        }
    }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AppColors.Blue.copy(alpha = 0.18f),
                            Color.White
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // ⬇️ Solo dejamos el header de perfil
                ProfileHeader(
                    name = name,
                    username = username,
                    city = selectedCity
                )
            }
        }
    }
}


        @Composable
        private fun ProfileHeader(
            name: String,
            username: String,
            city: City
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(1.dp, AppColors.Blue.copy(alpha = 0.15f)),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Blue.copy(alpha = 0.08f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(R.drawable.logo2),
                            contentDescription = "Logo de uniLocal"
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val greeting = name.takeIf { it.isNotBlank() }
                            ?.let { stringResource(R.string.txt_profile_greeting_with_name, it) }
                            ?: stringResource(R.string.txt_profile_greeting)

                        Text(
                            text = greeting,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.Blue
                        )

                        if (username.isNotBlank()) {
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, AppColors.Blue.copy(alpha = 0.1f))
                            ) {
                                Text(
                                    text = "@${username.trim()}",
                                    fontSize = 14.sp,
                                    color = AppColors.Blue,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Text(
                            text = stringResource(R.string.txt_profile_intro),
                            fontSize = 14.sp,
                            color = AppColors.GrayDark
                        )

                        if (city != City.DEFAULT) {
                            Text(
                                text = city.displayName,
                                fontSize = 12.sp,
                                color = AppColors.GrayDark
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileStat(value = "12", label = stringResource(R.string.txt_profile_places_label))
                        VerticalDivider(
                            modifier = Modifier
                                .height(36.dp),
                            color = AppColors.Blue.copy(alpha = 0.2f)
                        )
                        ProfileStat(value = "8", label = stringResource(R.string.txt_profile_reviews_label))
                        VerticalDivider(
                            modifier = Modifier
                                .height(36.dp),
                            color = AppColors.Blue.copy(alpha = 0.2f)
                        )
                        ProfileStat(value = "5", label = stringResource(R.string.txt_profile_favorites_label))
                    }
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        private fun CityPicker(
            selectedCity: City,
            onCitySelected: (City) -> Unit,
            modifier: Modifier = Modifier
        ) {
            var expanded by rememberSaveable { mutableStateOf(false) }

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCity.displayName,
                        onValueChange = {},
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        label = { Text(text = stringResource(R.string.txt_city)) },
                        leadingIcon = {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        City.entries.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(text = city.displayName) },
                                onClick = {
                                    onCitySelected(city)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = stringResource(R.string.txt_profile_city_helper),
                    fontSize = 12.sp,
                    color = AppColors.GrayDark,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        @Composable
        private fun ProfileLoadingState() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CircularProgressIndicator(color = AppColors.Blue)
                Text(
                    text = stringResource(R.string.txt_profile_loading),
                    fontSize = 14.sp,
                    color = AppColors.GrayDark
                )
            }
        }

        @Composable
        private fun ProfileStat(
            value: String,
            label: String,
            modifier: Modifier = Modifier
        ) {
            Column(
               // modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Blue
                )
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = AppColors.GrayDark
                )
            }
        }