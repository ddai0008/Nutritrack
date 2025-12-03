package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * This is the Customised OutlineTextField for this App
 * @param value is the value displayed
 * @param onValueChange pass the new values to its super
 * @param labelText text for the TextField
 * @param isError the Error State
 * @param hasDropdown check whether trailing icon is needed
 * @param readable provide immutable options
 */
@Composable
fun NutriTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,

    // Error Feedback
    isError: Boolean = false,

    // Handle Expand (Optional)
    hasDropdown: Boolean = false,
    expanded: Boolean = false,
    expandClick: () -> Unit = {},

    // Handle immutability,
    readable: Boolean = false,

    // Password Only
    isPassword: Boolean = false,

    // Visual Customisation
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    icon: ImageVector = Icons.Filled.KeyboardArrowDown
) {
    Column(modifier = modifier.widthIn(max = 500.dp)) {
        OutlinedTextField(
            // Value Response
            value = value,
            onValueChange = { onValueChange(it) },

            // Text Style
            label = {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.titleMedium
                )
            },

            // Visual Appearance
            shape = shape,
            modifier = Modifier.fillMaxWidth(),

            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,

            // State of the button
            isError = isError,
            readOnly = readable,

            // Trailing Icon, only if needed
            trailingIcon = {
                if (hasDropdown) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Expand Options",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50f))
                            .clickable { expandClick() }
                            .rotate(if (expanded) 180f else 0f)
                    )
                }
            }
        )
    }
}