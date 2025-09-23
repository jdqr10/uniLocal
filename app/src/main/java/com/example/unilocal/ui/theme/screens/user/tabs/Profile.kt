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
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unilocal.R
import com.example.unilocal.ui.theme.AppColors
import com.example.unilocal.ui.theme.components.InputText

@Composable
fun Profile(){

    var name by rememberSaveable { mutableStateOf("") }
    var user by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(R.drawable.logo2),
                contentDescription = "Logo de uniLocal"
            )

            Text(
                text = stringResource(R.string.txt_update_profile),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Blue
            )

            InputText(
                value = name,
                label = stringResource(R.string.txt_name),
                supportingText = stringResource(R.string.txt_name_error),
                onValueChange = {
                    name = it
                },
                onValidate = {
                    name.isBlank()
                },
                icon = Icons.Outlined.Person
            )

            InputText(
                value = user,
                label = stringResource(R.string.txt_user),
                supportingText = stringResource(R.string.txt_user_error),
                onValueChange = {
                    user = it
                },
                onValidate = {
                    user.isBlank()
                },
                icon = Icons.Outlined.Person
            )

            InputText(
                value = city,
                label = stringResource(R.string.txt_city),
                supportingText = stringResource(R.string.txt_city),
                onValueChange = {
                    city = it
                },
                onValidate = {
                    city.isBlank()
                },
                icon = Icons.Outlined.LocationOn
            )

            Button(
                onClick = {

                },

                modifier = Modifier.width(290.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Orange
                ),
                content = {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(R.string.btn_update_profile),
                    )
                }
            )

        }


    }
}