package com.example.unilocal.ui.theme.screens

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.unilocal.R
import com.example.unilocal.ui.theme.AppColors
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unilocal.model.City
import com.example.unilocal.model.DisplayableEnum
import com.example.unilocal.model.Role
import com.example.unilocal.model.User
import com.example.unilocal.ui.theme.components.DropdownMenu
import com.example.unilocal.ui.theme.components.InputText
import com.example.unilocal.ui.theme.components.OperationResultHandler
import java.util.UUID

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {}
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val userResult by usersViewModel.userResult.collectAsState()


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var user by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries
    var country by remember { mutableStateOf("") }

    val countryList = listOf("Colombia", "Peru", "Ecauador", "Brasil", "Bolivia")
    val cityList = listOf("Bogota", "Lima", "Quito", "Caracas", "La Paz")

    val context = LocalContext.current

    val fieldModifier = Modifier.width(280.dp)

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
                text = stringResource(R.string.txt_register_account),
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

//            DropdownMenu(
//                label = stringResource(R.string.txt_country),
//                supportingText = stringResource(R.string.txt_country_error),
//                list = countryList,
//                icon = Icons.Outlined.Place,
//
//                onValueChange = {
//                    country = it
//                },
//                modifier = fieldModifier
//            )

            InputText(
                value = email,
                label = stringResource(R.string.txt_email),
                supportingText = stringResource(R.string.txt_email_error),
                onValueChange = {
                    email = it
                },
                onValidate = {
                    email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                icon = Icons.Outlined.Email
            )

            InputText(
                value = password,
                isPassword = true,
                label = stringResource(R.string.txt_password),
                supportingText = stringResource(R.string.txt_password_error),
                onValueChange = {
                    password = it
                },
                onValidate = {
                    password.length < 5 || password.isBlank()
                },
                icon = Icons.Outlined.Lock
            )

            InputText(
                value = confirmPassword,
                isPassword = true,
                label = stringResource(R.string.txt_confirmPassword),
                supportingText = stringResource(R.string.txt_confirmPassword_error),
                onValueChange = {
                    confirmPassword = it
                },
                onValidate = {
                    password != confirmPassword
                },
                icon = Icons.Outlined.Lock
            )


            Button(
                onClick = {
                    val user = User(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        username = user,
                        city = city as City,
                        email = email,
                        role = Role.USER,
                        password = password
                    )
                    usersViewModel.create(
                        user
                    )
//                    Toast.makeText(context, "Su registro fue exitoso", Toast.LENGTH_SHORT).show()
                },

                modifier = Modifier.width(290.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Orange
                ),
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = stringResource(R.string.btn_register)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(R.string.btn_register),
                    )
                }
            )

            OperationResultHandler(
                result = userResult,
                onSuccess = {
                    onNavigateToLogin()
                    usersViewModel.resetOperationResult()
                },
                onFailure = {
                    usersViewModel.resetOperationResult()
                }
            )

        }
    }

}
