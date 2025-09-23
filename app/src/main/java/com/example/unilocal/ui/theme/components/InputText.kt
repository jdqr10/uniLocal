package com.example.unilocal.ui.theme.components

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.unilocal.R
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    value : String,
    label : String,
    supportingText : String,
    onValueChange : (String) -> Unit,
    onValidate : (String) -> Boolean,
    icon: ImageVector? = null
){
    var isError by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        // modifier = modifier.fillMaxWidth(),
        label = {
            Text(text = label)
        },
        value = value,
        isError = isError,
        supportingText = {
            if (isError){
                Text(text = supportingText)
            }
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            if (icon != null){
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.txt_email)
                )
            }
        },
        onValueChange = {
            onValueChange(it)
            isError = onValidate(it)
            // isEmailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    )
}