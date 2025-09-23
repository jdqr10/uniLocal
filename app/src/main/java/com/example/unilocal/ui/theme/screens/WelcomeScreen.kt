package com.example.unilocal.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unilocal.R
import com.example.unilocal.ui.theme.AppColors

@Composable
fun WelcomScreen(
    onNavigateToLogin: () -> Unit = {}
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterVertically)
    ){
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)) {
                    append("Bienvenido a\n")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp, color = AppColors.Orange)) {
                    append("uniLocal")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
        )

        Image(
            modifier = Modifier.width(200.dp),
            painter = painterResource(R.drawable.welcome),
            contentDescription = "Logo de frisby"
        )

        Button(
            onClick = {
                onNavigateToLogin()
            },

            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Orange
            ),
            content = {
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Continuar",
                )
            }
        )

    }
}