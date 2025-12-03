package com.daviddai.assignment3_33906211.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.ui.theme.secondaryColor

/**
 * This is the Custom Scaffold applying a generic format
 * @param modifier is the modifier applied
 * @param paddingValues is the padding of the scaffold
 * @param backgroundColor is the color of the container
 * @return a custom scaffold
 */
@Composable
fun NutriScaffold(
    modifier: Modifier = Modifier,

    // Visual Adjustment
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    backgroundColor: Color = secondaryColor,

    // Content
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = backgroundColor,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement
        ) {
            content()
        }
    }
}