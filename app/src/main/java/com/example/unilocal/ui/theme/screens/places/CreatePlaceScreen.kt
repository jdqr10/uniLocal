package com.example.unilocal.ui.theme.screens.places

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unilocal.model.City
import com.example.unilocal.model.DisplayableEnum
import com.example.unilocal.model.PlaceType
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID
import com.example.unilocal.R
import com.example.unilocal.model.Location
import com.example.unilocal.model.Place
import com.example.unilocal.model.Schedule
import com.example.unilocal.ui.theme.components.DropdownMenu
import com.example.unilocal.ui.theme.components.InputText



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaceScreen(
    userId: String?,
    onNavigateBack: () -> Unit
){

    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries

    var type by remember { mutableStateOf<DisplayableEnum>(PlaceType.RESTAURANT) }
    val types = PlaceType.entries

    val schedule = remember { mutableStateListOf(
        Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
    ) }

    var title by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }


    BackHandler(
        enabled = !showExitDialog
    ){
        showExitDialog = true
    }

    Scaffold (
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_create_place))
                },
                navigationIcon = {
                    IconButton (
                        onClick = {
                            showExitDialog = true
                        }
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )

        }
    ) {

        Box(
            modifier = Modifier.padding(it)
        ){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                TitleForm(
                    title = stringResource(R.string.title_general_info)
                )

                InputText(
                    value = title,
                    label = stringResource(R.string.txt_title),
                    supportingText = stringResource(R.string.txt_title_error),
                    onValueChange = {
                        title = it
                    },
                    onValidate = {
                        title.isBlank()
                    },
                    icon = Icons.Filled.Storefront
                )

                InputText(
                    value = description,
                    label = stringResource(R.string.txt_description),
                    supportingText = stringResource(R.string.txt_description_error),
                    multiline = true,
                    onValueChange = {
                        description = it
                    },
                    onValidate = {
                        description.isBlank()
                    },
                    icon = Icons.Filled.Description
                )

                DropdownMenu(
                    label = stringResource(R.string.txt_city),
                    supportingText = stringResource(R.string.txt_city_error),
                    list = cities,
                    icon = Icons.Outlined.Place,
                    onValueChange = {
                        city = it
                    }
                )

                InputText(
                    value = address,
                    label = stringResource(R.string.txt_address),
                    supportingText = stringResource(R.string.txt_address_error),
                    onValueChange = {
                        address = it
                    },
                    onValidate = {
                        address.isBlank()
                    },
                    icon = Icons.Filled.Directions
                )

                DropdownMenu(
                    label = stringResource(R.string.txt_category),
                    supportingText = stringResource(R.string.txt_category_error),
                    list = types,
                    icon = Icons.Outlined.Category,
                    onValueChange = {
                        type = it
                    }
                )

                InputText(
                    value = phoneNumber,
                    label = stringResource(R.string.txt_phone_number),
                    supportingText = stringResource(R.string.txt_phone_number_error),
                    onValueChange = {
                        phoneNumber = it
                    },
                    onValidate = {
                        phoneNumber.isBlank()
                    },
                    icon = Icons.Filled.Phone
                )

                TitleForm(
                    title = stringResource(R.string.title_upload_photos)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier.size(70.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(0.dp) // sin padding extra, icono centrado
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddAPhoto,
                            contentDescription = null
                        )
                    }

                }

                TitleForm(
                    title = stringResource(R.string.title_schedule)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    OutlinedButton(
                        onClick = {

                        },
                        modifier = Modifier.size(70.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(0.dp) // sin padding extra, icono centrado
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null
                        )
                    }

                    schedule.forEach { item ->
                        Column {
                            Text(
                                text = item.toDisplayString(),
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = item.open.toString()
                                )
                                Text(
                                    text = "-"
                                )
                                Text(
                                    text = item.close.toString()
                                )
                            }

                        }
                    }

                }

                TitleForm(
                    title = stringResource(R.string.title_location)
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(R.drawable.map),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )


                Button(
                    onClick = {

                        val place = Place(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            description = description,
                            address = address,
                            city = city as City,
                            location = Location(1.0, 1.0),
                            images = listOf(),
                            phoneNumber = phoneNumber,
                            type = type as PlaceType,
                            schedules = schedule,
                            ownerId = userId ?: ""
                        )

                        Log.d("CREATE", place.toString())

                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = stringResource(R.string.btn_create_place)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = stringResource(R.string.btn_create_place))
                }

            }

        }

    }

    if(showExitDialog){

        AlertDialog(
            title = {
                Text(text = "Está seguro de salir?")
            },
            text = {
                Text(text = "Si sale perderá los cambios")
            },
            onDismissRequest = {
                showExitDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )

    }

}

@Composable
fun TitleForm(
    title: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp, top = 5.dp),
        contentAlignment = Alignment.Center
    ) {

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.LightGray,
            thickness = 1.dp
        )

        Text(
            text = title,
            modifier = Modifier
                .background( MaterialTheme.colorScheme.surface)
                .padding(horizontal = 8.dp)
        )
    }
}