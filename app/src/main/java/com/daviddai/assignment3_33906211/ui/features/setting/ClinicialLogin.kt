package com.daviddai.assignment3_33906211.ui.features.setting


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daviddai.assignment3_33906211.data.viewModel.LoginViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriFullDialog
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTextField
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor

/**
 * Display a dialog for clinician login.
 */
@Composable
fun ClinicialLogin(
    onDismiss: () -> Unit,
    viewModel: LoginViewModel,
    navController: NavController
) {
    val passwordError = viewModel.clinicError

    // Remember Scroll Position to handle rotation
    val scrollState = rememberScrollState()

    NutriFullDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Center Title
            NutriLargeTitle(
                title = "Clinician Login",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Password TextField
            NutriTextField(
                value = viewModel.clinicEntry,
                onValueChange = { viewModel.onClinicEntryChanged(it) },
                labelText = "Clinician Key",
                isError = passwordError.isError,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ErrorMessage(passwordError.message, passwordError.isError)

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            NutriTrackButton(
                onClick = {
                    viewModel.adminLogin(
                        onSuccess = {
                            navController.navigate("ClinicianScreen")
                        }
                    )
                },
                enabled = !viewModel.isAccountLocked
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Login,
                    contentDescription = "Clinician Login",
                    tint = onPrimaryColor
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Login",
                    color = onPrimaryColor,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Return Button
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onDismiss() }) {
                Text(
                    text = "Return",
                    color = onSecondaryColor,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

/**
 * Display an error message if there is an error.
 * @param text The error message to display.
 * @param isError Whether there is an error or not.
 */
@Composable
fun ErrorMessage(text: String, isError: Boolean = true) {
    if (!isError) return

    Text(
        text = text,
        color = Color.Red,
        fontStyle = FontStyle.Italic,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 4.dp)
    )
}