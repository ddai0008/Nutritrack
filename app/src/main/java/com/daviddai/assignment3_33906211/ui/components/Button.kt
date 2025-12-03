package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.ui.theme.disabledGradient
import com.daviddai.assignment3_33906211.ui.theme.orangeGradient


/*
 * Reference:
 * https://stackoverflow.com/questions/75950471/how-to-make-rounded-corner-with-shadow-in-jetpack-compose
 * https://github.com/android/compose-samples/tree/main/Jetsnack
 */

/**
 * This is a Custom Button of the App
 * @param buttonPadding is the exterior padding
 * @param contentPadding is the inner padding
 * @param elevation is the shadow
 * @param cornerRadius is the corner shape
 * @return Onclick function
 */
@Composable
fun NutriTrackButton(
    // Main Parameter
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier.fillMaxWidth(),

    // Visual Adjustment
    buttonPadding: PaddingValues = PaddingValues(8.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    elevation: Dp = 5.dp, // Box Shadow
    buttonShape: CornerBasedShape = shapes.large,
    enabled: Boolean = true,

    // Content
    content: @Composable () -> Unit
) {
    val backgroundBrush = if (enabled) orangeGradient else disabledGradient
    val shadowElevation = if (enabled) elevation else 0.dp

    Box(
        modifier = modifier
            .widthIn(max = 500.dp)
            .padding(buttonPadding)
            .shadow(elevation = shadowElevation, shape = buttonShape)
            .background(brush = backgroundBrush, shape = buttonShape)
            .clickable(onClick = onClick, enabled = enabled),
    ) {
        Row(
            modifier = buttonModifier.padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pass in the Content
            content()
        }
    }
}