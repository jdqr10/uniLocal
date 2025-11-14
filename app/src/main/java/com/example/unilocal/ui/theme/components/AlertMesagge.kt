// com/example/unilocal/ui/theme/components/AlertMesagge.kt
package com.example.unilocal.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AlertMesagge(
    modifier: Modifier = Modifier,
    type: AlertType,
    message: String,
    visible: Boolean = true,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 0.dp,
            shadowElevation = 2.dp,
            color = type.containerColor
        ) {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícono
                Icon(
                    imageVector = type.icon,
                    contentDescription = type.title,
                    tint = type.accentColor,
                    modifier = Modifier.size(26.dp)
                )

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = type.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = type.contentColor
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = type.contentColor.copy(alpha = 0.9f)
                    )

                    if (actionLabel != null && onAction != null) {
                        Spacer(Modifier.height(8.dp))
                        TextButton(
                            onClick = onAction,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(actionLabel)
                        }
                    }
                }

                if (onDismiss != null) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Cerrar",
                            tint = type.contentColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

enum class AlertType(
    val title: String,
    val icon: ImageVector,
    val accentColor: Color,
    val containerColor: Color,
    val contentColor: Color
) {
    SUCCESS(
        title = "¡Éxito!",
        icon = Icons.Rounded.CheckCircle,
        accentColor = Color(0xFF28A745),
        containerColor = Color(0xFFD1F2E2),
        contentColor = Color(0xFF0B1F16)
    ),
    ERROR(
        title = "Algo salió mal",
        icon = Icons.Rounded.Error,
        accentColor = Color(0xFFB00020),
        containerColor = Color(0xFFF8D7DA),
        contentColor = Color(0xFF2B0E10)
    );
}
