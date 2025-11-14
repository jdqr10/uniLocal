package com.example.unilocal.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.unilocal.utils.RequestResult
import kotlinx.coroutines.delay

@Composable
fun OperationResultHandler(
    result: RequestResult?,
    onSuccess: suspend () -> Unit,
    onFailure: suspend () -> Unit
) {

    when (result)
    {
        is RequestResult.Loading -> {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
        is RequestResult.Success -> {
        AlertMesagge(type = AlertType.SUCCESS, message = result.message)

    }
        is RequestResult.Failure -> {
       AlertMesagge(type = AlertType.ERROR, message = result.errorMessage)

    }
        else ->{}
    }

    LaunchedEffect(result) {
        when (result) {
            is RequestResult.Success -> {
                delay(2000)
                onSuccess()
            }

            is RequestResult.Failure -> {
                delay(4000)
                onFailure()
            }

            else -> {}
        }

    }
}