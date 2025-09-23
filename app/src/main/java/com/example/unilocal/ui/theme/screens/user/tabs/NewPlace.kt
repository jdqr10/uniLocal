package com.example.unilocal.ui.theme.screens.user.tabs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R
import com.example.unilocal.ui.theme.AppColors
import com.example.unilocal.ui.theme.components.DropdownMenu
import com.example.unilocal.ui.theme.components.InputText

@Composable
fun NewPlace(){

    var name by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf("") }

    val categoryList = listOf("Rstaurante", "Bar", "Cafe", "Hotek")

    Surface {
        //Text(text = "New Place")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Image(
                modifier = Modifier.width(270.dp),
                painter = painterResource(R.drawable.location),
                contentDescription = "Logo de frisby"
            )

            // Formulario
            InputText(
                value= name,
                label = stringResource(R.string.txt_name_place),
                supportingText = stringResource(R.string.txt_place_error),
                onValueChange = { name = it },
                onValidate = {
                    name.isBlank()
                },
                icon = Icons.Outlined.Person,
               // modifier = Modifier.fillMaxWidth()

            )

            DropdownMenu(
                label = stringResource(R.string.txt_country),
                supportingText = stringResource(R.string.txt_country_error),
                list = categoryList,
                icon = Icons.Outlined.LocationOn,
                onValueChange = {
                    category = it
                },
                modifier = Modifier.width(280.dp)

            )

            InputText(
                value = phone,
                label = stringResource(R.string.txt_phone),
                supportingText = stringResource(R.string.txt_phone_error),
                onValueChange = {
                    phone = it
                },
                onValidate = {
                    phone.isBlank() || phone.length < 10
                },
                icon = Icons.Outlined.Phone
            )

            InputText(
                value = direction,
                label = stringResource(R.string.txt_direction),
                supportingText = stringResource(R.string.txt_direction_error),
                onValueChange = {
                    direction = it
                },
                onValidate = {
                    direction.isBlank()
                },
                icon = Icons.Outlined.LocationOn
            )

            Button(
                onClick = {

                    Log.d("NewPlaceScreen", "Nombre: $name, Usuario: $category, Telefono: $phone, Correo: $direction")
                },

                modifier = Modifier.width(290.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Orange
                ),
                content = {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(R.string.btn_new_pace),
                    )
                }
            )
        }
    }
}