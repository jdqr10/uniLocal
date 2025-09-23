package com.example.unilocal.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.unilocal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    label: String,
    list: List<String>,
    icon: ImageVector,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = {
                Text(text = label)
            },
            supportingText = {

            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.txt_city)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            modifier = modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEach{
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        onValueChange(selectedItem)
                        selectedItem = it
                        expanded = false
                    }
                )
            }
        }
    }
}