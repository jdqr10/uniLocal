package com.example.unilocal.ui.theme.screens

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.unilocal.R
import com.example.unilocal.ui.theme.AppColors
import com.example.unilocal.ui.theme.components.InputText
import com.example.unilocal.viewmodel.UsersViewModel
import com.example.unilocal.model.Role
import com.example.unilocal.ui.theme.components.OperationResultHandler


@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit ,
    onNavigateToHome: (String, Role) -> Unit
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val userResult by usersViewModel.userResult.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo + texto centrados como grupo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally) // fuerza al centro
            ) {
                Image(
                    modifier = Modifier.width(80.dp),
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = stringResource(id = R.string.txt_image)
                )
                Text(
                    text = "uniLocal",
                    fontSize = 40.sp,
                    color = AppColors.Blue,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.padding(top = 24.dp))

            // Input
            InputText(
                value = email,
                label = stringResource(R.string.txt_email),
                supportingText = stringResource(R.string.txt_email_error),
                onValueChange = { email = it },
                onValidate = {
                    email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                icon = Icons.Outlined.Email,
                modifier = Modifier.fillMaxWidth()
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

            TextButton(
                onClick = {
                    onNavigateToRegister()
                },
                content = {
                    Text(
                        text = stringResource(R.string.btn_create_account)
                    )
                }
            )

            Button(
                onClick = {
                     usersViewModel.login(email, password)


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
                        text = stringResource(R.string.btn_inicio_sesion),
                    )
                }
            )



//            Button(
//                onClick = {
//                   onNavigateToRegister()
//                },
//
//                modifier = Modifier.width(290.dp),
//                shape = RoundedCornerShape(5.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = AppColors.Blue
//                ),
//                content = {
//                    Icon(
//                        imageVector = Icons.Outlined.AddCircle,
//                        contentDescription = stringResource(R.string.btn_create_account)
//                    )
//                    Spacer(modifier = Modifier.width(5.dp))
//                    Text(
//                        text = stringResource(R.string.btn_create_account),
//                    )
//                }
//            )

            OperationResultHandler(
                result = userResult,
                onSuccess = {
                    onNavigateToHome(usersViewModel.currentUser.value!!.id, usersViewModel.currentUser.value!!.role)
                    usersViewModel.resetOperationResult()
                },
                onFailure = {
                    usersViewModel.resetOperationResult()
                }
            )


        }
    }

}


