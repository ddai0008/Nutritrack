package com.daviddai.assignment3_33906211.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.daviddai.assignment3_33906211.ui.features.questionnaires.categories.Persona
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.imageGradient
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor

/**
 * This is the Composable for the Persona Dialog
 * @param persona is the persona to display
 * @param onDismiss is the on dismiss request
 */
@Composable
fun PersonaDialog(
    persona: Persona,
    onDismiss: () -> Unit,
) {
    // Reference: https://stackoverflow.com/questions/64753944/orientation-on-jetpack-compose
    // Check the device orientation, where it is landscape or portrait
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Display the Dialog
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Ignore the default size
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = backgroundColor,
            tonalElevation = 2.dp,
            modifier = Modifier
                .widthIn(max = if (isLandscape) 600.dp else 400.dp)
                .padding(32.dp)
        ) {
            if (isLandscape) {
                // Landscape layout - image on left
                LandscapeLayout(
                    persona = persona
                )
            } else {
                // Portrait layout - image above text
                PortraitLayout(
                    onDismiss = onDismiss,
                    persona = persona
                )
            }
        }
    }
}

/**
 * This is the Composable for the Persona Dialog Content (Landscape)
 */
@Composable
private fun LandscapeLayout(
    persona: Persona,
) {
    Row {
        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = persona.imageRes),
                contentDescription = persona.name,
            )
            Box(modifier = Modifier
                .matchParentSize()
                .background(imageGradient))
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(
                text = persona.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = persona.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * This is the Composable for the Persona Dialog Content (Portrait)
 * @param persona is the persona to display
 */
@Composable
private fun PortraitLayout(
    persona: Persona,
    onDismiss: () -> Unit
) {
    Column {
        Box {
            Image(
                painter = painterResource(id = persona.imageRes),
                contentDescription = persona.name,
            )
            Box(modifier = Modifier
                .matchParentSize()
                .background(imageGradient))
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = persona.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = persona.description,
                style = MaterialTheme.typography.bodyMedium
            )

            NutriTrackButton(
                onClick = { onDismiss() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "back",
                    color = onPrimaryColor,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
