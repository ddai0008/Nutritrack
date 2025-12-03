package com.daviddai.assignment3_33906211.ui.features.login


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.infoColor
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor
import com.daviddai.assignment3_33906211.ui.theme.secondaryColor


/**
 * This is the Composable for Disclaimer
 * @return dismiss and accept the terms and condition
 */
@Composable
fun DisclaimerDialog(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    // External Monash URL
    val nutritionClinicUrl =
        "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition"

    // URL Handler
    val uriHandler = LocalUriHandler.current

    AlertDialog(
        modifier = modifier,
        containerColor = secondaryColor,
        onDismissRequest = {}, // Not Allow to Click Outside

        // This is in the Components files
        title = { NutriLargeTitle("Health Disclaimer") },

        // Transfer user to the Link
        text = { DialogContent(onLinkClick = { uriHandler.openUri(nutritionClinicUrl) }) },

        confirmButton = { DialogConfirmButton(onConfirm = onConfirm) }
    )
}

/**
 * This is the Composable for Disclaimer Content
 * @return a function that perform url click
 */
@Composable
private fun DialogContent(
    onLinkClick: () -> Unit
) {
    // Remember Scroll Position
    val scrollState = rememberScrollState()
    // Special Style for the content
    val bodyLargeItalic = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic)

    Column(
        modifier = Modifier
            .height(210.dp) // Fixed Height For Scrollability
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.disclaimer_text),
            style = bodyLargeItalic,
            color = onSecondaryColor
        )

        Text(
            text = "monash nutrition clinics",
            style = bodyLargeItalic,
            color = infoColor,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable(onClick = onLinkClick)
                .padding(top = 8.dp)
        )
    }
}

/**
 * This is the Composable for Disclaimer Dismiss Button
 * @return dismiss and accept the terms and condition
 */
@Composable
private fun DialogConfirmButton(
    onConfirm: () -> Unit
) {
    NutriTrackButton(
        onClick = onConfirm,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "I accept",
            color = onPrimaryColor,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}