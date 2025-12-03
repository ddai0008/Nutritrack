package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor

/**
 * This is the Composable for Title
 * @return a Title that centered and formatted
 */
@Composable
fun NutriLargeTitle(
    title: String,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = onSecondaryColor,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = style,
            fontWeight = fontWeight,
            color = color,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}