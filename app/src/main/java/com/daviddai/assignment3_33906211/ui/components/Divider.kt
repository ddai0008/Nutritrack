package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.ui.theme.mediumGray

/**
 * This is a Custom Divider of the App
 * @param modifier is the modifier
 * @param thickness is the thickness
 * @param color is the color
 */
@Composable
fun NutriDivider(
    modifier: Modifier = Modifier.padding(vertical = 32.dp),
    thickness: Dp = 2.dp,
    elevation: Dp = 0.dp,
    color: Color
) {
    HorizontalDivider(
        thickness = thickness,
        color = color,
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation)
    )
}