package com.daviddai.assignment3_33906211.ui.features.login


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.data.login.AuthManager
import com.daviddai.assignment3_33906211.data.viewModel.RegisterViewModel
import com.daviddai.assignment3_33906211.data.viewModel.LoginViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriScaffold
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.features.dashboard.Dashboard
import com.daviddai.assignment3_33906211.ui.theme.NutriTrackTheme
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor

/*
    Logo is generated using google gemini
 */

/**
 * This is the Main Screen / Welcome Screen of the APP
 */
@Composable
fun NutriTrackApp() {
    NutriTrackTheme {
        // Variable to keep track of display element
        var showDisclaimer by rememberSaveable { mutableStateOf(true) }
        var showLoginForm by rememberSaveable { mutableStateOf(false) }
        var showRegisterForm by rememberSaveable { mutableStateOf(false) }

        // ViewModel for Sub Composable
        val userViewModel: LoginViewModel = viewModel()
        val registerViewModel: RegisterViewModel = viewModel()

        val context = LocalContext.current

        NutriScaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display Student Detail
                StudentDisplay("Student: David Dai (33906211)")

                // Logo Display
                Image(
                    painter = painterResource(id = R.drawable.nutritrack_logo),
                    contentDescription = "NutriTrack Logo",
                    // Expand and push button to the bottom
                    modifier = Modifier.weight(1f)
                )

                NutriTrackButton(
                    onClick = {
                        // Check if login exist
                        val loginExist = userViewModel.checkSharePrefExist(context)

                        // Skip Login if login exist
                        if (loginExist) {
                            context.startActivity(Intent(context, Dashboard::class.java))
                            AuthManager.login(userViewModel.getSharePref(context).toString())
                        } else {
                            showLoginForm = true
                        }
                    },
                    buttonPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleLarge,
                        color = onPrimaryColor
                    )
                }

                // Handle disclaimer display
                if (showDisclaimer) {
                    DisclaimerDialog(onConfirm = { showDisclaimer = false })
                }

                // Handle Login display
                if (showLoginForm) {
                    LoginDialog(
                        userViewModel,
                        onDismiss = { showLoginForm = false },
                        onRegisterClick = { showRegisterForm = true })
                }

                // Handle Register display
                if (showRegisterForm) {
                    RegisterDialog(
                        registerViewModel,
                        onDismiss = {
                            showRegisterForm = false
                            showLoginForm = true
                        }
                    )
                }
            }
        }
    }
}


/**
 * This is the Student Detail
 * @param studentDetail is the Student Detail
 */
@Composable
fun StudentDisplay(studentDetail: String) {
    Text(
        text = studentDetail,
        style = MaterialTheme.typography.bodyMedium,
        fontStyle = FontStyle.Italic,
        color = onSecondaryColor
    )
}


