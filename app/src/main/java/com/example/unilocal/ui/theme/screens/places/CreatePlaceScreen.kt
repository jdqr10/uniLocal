package com.example.unilocal.ui.theme.screens.places

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
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
import com.example.unilocal.ui.theme.components.Map
import com.example.unilocal.ui.theme.components.OperationResultHandler
import com.example.unilocal.ui.theme.screens.LocalMainViewModel
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaceScreen(
    userId: String?,
    onNavigateBack: () -> Unit
){


    val context = LocalContext.current

    var image by remember { mutableStateOf("") }

    val placeViewModel = LocalMainViewModel.current.placesViewModel
    val placeResult by placeViewModel.placeResult.collectAsState()


    var clickedPoint by rememberSaveable { mutableStateOf<Point?>(null) }
    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries

    var type by remember { mutableStateOf<DisplayableEnum>(PlaceType.RESTAURANT) }
    val types = PlaceType.entries

    val schedule = remember { mutableStateListOf(
        Schedule(DayOfWeek.MONDAY, Date(), Date()) //LocalTime.of(9, 0), LocalTime.of(18, 0))
    ) }

    var title by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }

    val config = mapOf(
        "cloud_name" to "dg20g8ffd",
        "api_key" to "325721746529343",
        "api_secret" to "mSP_k1BcLKWnfuAxpR4Zq7xNCiw"
    )

    val scope = rememberCoroutineScope()
    val cloudinary = Cloudinary(config)

    val fileLaucher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){ uri: Uri? ->
        uri?.let{
            scope.launch(Dispatchers.IO){
                val inputStream = context.contentResolver.openInputStream(it)
                inputStream?.use { stream ->
                    val result = cloudinary.uploader().upload(stream, ObjectUtils.emptyMap())
                    val imageUrl = result["secure_url"].toString()
                    image = imageUrl
                }
            }
        }

    }

    val permissonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){
        if(it){
            Toast.makeText(
                context,
                "Permiso concedido",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            Toast.makeText(
                context,
                "Permiso denegado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



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
                            val permissonCheckResult = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                            }else{
                                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                            }

                            if(permissonCheckResult == PackageManager.PERMISSION_GRANTED){
                                fileLaucher.launch("image/*")
                            }else{
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    permissonLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                }else{
                                    permissonLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        },

                        modifier = Modifier.size(70.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddAPhoto,
                            contentDescription = null
                        )
                    }

                    AsyncImage(
                        modifier = Modifier.width(120.dp).height(100.dp).clip(RoundedCornerShape(16.dp)),
                        model = image,
                        contentDescription = null,
                    )

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
                        contentPadding = PaddingValues(0.dp)
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

                Map (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    activateClick = true,
                    onMapClickListener = {l ->
                        clickedPoint = l
                    }
                )

                OperationResultHandler(
                    result = placeResult,
                    onSuccess = {
                        onNavigateBack()
                        placeViewModel.resetOperationResult()
                    },
                    onFailure = {
                        placeViewModel.resetOperationResult()
                    }
                )


                Button(
                    onClick = {

                        if(clickedPoint != null){
                            val place = Place(
                                id = "",
                                title = title,
                                description = description,
                                address = address,
                                city = city as City,
                                location = Location(clickedPoint!!.latitude(), clickedPoint!!.longitude()),
                                images = listOf(image),
                                phoneNumber = phoneNumber,
                                type = type as PlaceType,
                                schedules = schedule,
                                ownerId = userId ?: "",
                                status = Place.STATUS_PENDING
                            )

                            placeViewModel.create(place)


                        }

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

//@Composable
//fun Cloudinary(x0: Map<String, String>) {
//    TODO("Not yet implemented")
//}

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