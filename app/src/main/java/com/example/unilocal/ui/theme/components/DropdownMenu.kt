package com.example.unilocal.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R
import com.example.unilocal.model.DisplayableEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    label: String,
    list: List<DisplayableEnum>,
    icon: ImageVector,
    onValueChange: (DisplayableEnum) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String,
    enabled: Boolean = true
) {

    var isError by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(list.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded && enabled,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            enabled = enabled,
            readOnly = true,
            value = selectedItem.displayName,
            shape = RoundedCornerShape(15.dp),
            onValueChange = { },
            label = {
                Text(
                    text = label
                )
            },
            isError = isError,
            supportingText = {
                if(isError) {
                    Text(text = supportingText)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = label
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded && enabled) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded && enabled,
            onDismissRequest = { expanded = false }
        ){
            list.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.displayName)
                    },
                    onClick = {
                        selectedItem = it
                        onValueChange(selectedItem)
                        expanded = false
                    }
                )
            }
        }
    }
}