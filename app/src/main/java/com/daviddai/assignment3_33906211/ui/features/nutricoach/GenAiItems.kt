package com.daviddai.assignment3_33906211.ui.features.nutricoach


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.ui.theme.Shapes
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.primaryColor


/**
 * Display the Composable for GenAi Items
 * @param content the content to display
 */
@Composable
fun GenAiItems(
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(50.dp))
                .border(1.dp, color = primaryColor, shape = RoundedCornerShape(50.dp))
                .padding(8.dp)
        ) {
            Icon(
                Icons.Filled.Person3,
                contentDescription = "AI",
                Modifier.size(24.dp),
                tint = primaryColor
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .shadow(elevation = 6.dp, shape = Shapes.large)
                .background(color = backgroundColor, shape = Shapes.large)
                .border(1.dp, color = primaryColor, shape = Shapes.large)
                .padding(16.dp)
        ) {
            content()
        }
    }
}


