package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * This is the Customised OutlineTextField for this App
 * @param value is the value displayed
 * @param onValueChange pass the new values to its super
 * @param labelText text for the TextField
 * @param isError the Error State
 * @param displayList is the list to display on drop down item
 * @param readOnly provide immutable options
 */
@Composable
fun <Type> NutriDropDownInput(
    modifier: Modifier = Modifier,
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit = {},

    // Handle Non String List
    onClick: (Type) -> Unit,

    // Error feedback
    isError: Boolean = false,

    // List to Display
    displayList: List<Type>,
    itemName: (Type) -> String = { it.toString() },

    // Handle Optional Readable only
    readOnly: Boolean = false,
) {
    // Handle State of the Expand Display
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .widthIn(max = 500.dp)
            .fillMaxWidth()
    ) {
        // Input Field
        NutriTextField(
            value = value,
            onValueChange = { onValueChange(it) }, // Pass on the change
            labelText = labelText,

            isError = isError,

            hasDropdown = true,
            expanded = expanded,
            expandClick = { expanded = !expanded },

            readable = readOnly
        )

        // Dropdown Menu
        if (displayList.isNotEmpty()) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(150.dp) // Set Fixed Height to scroll
            ) {
                // Menu Items
                displayList.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onClick(item)
                            expanded = false
                        },
                        text = {
                            Text(
                                text = itemName(item),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }
    }
}