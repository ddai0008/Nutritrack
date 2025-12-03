package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor


/**
 * This is the Composable for the Full Dialog
 * @param onDismiss is the on dismiss request
 * @param modifier is the modifier
 * @param color is the color
 */
@Composable
fun NutriFullDialog(
    // Handle on dismiss request
    onDismissRequest: () -> Unit,

    // Visual Element
    modifier: Modifier = Modifier,
    color: Color = backgroundColor,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false), // Ignore the default size
    ) {
        Surface(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            content()
        }
    }
}