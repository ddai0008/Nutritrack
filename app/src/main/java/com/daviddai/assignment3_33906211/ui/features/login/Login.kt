package com.daviddai.assignment3_33906211.ui.features.login


import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.data.viewModel.LoginViewModel
import com.daviddai.assignment3_33906211.data.login.ValidationResult
import com.daviddai.assignment3_33906211.ui.components.NutriDropDownInput
import com.daviddai.assignment3_33906211.ui.components.NutriFullDialog
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTextField
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.features.dashboard.Dashboard
import com.daviddai.assignment3_33906211.ui.features.questionnaires.QuestionnaireScreen
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor
import kotlin.jvm.java

/**
 * This is the Login Dialog
 * @param viewModel is the ViewModel
 * @param onDismiss is the on dismiss request
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginDialog(
    viewModel: LoginViewModel,
    onDismiss: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val context = LocalContext.current

    val users by viewModel.users.collectAsState()
    val idList = users.map { it.userId }

    // Track Error
    val passwordError = viewModel.passwordError
    val idError = viewModel.idError

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
                title = "Login",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // The DropDown Input with Input capability
            NutriDropDownInput<String>(
                value = viewModel.userID,
                onValueChange = { viewModel.onUserIdChanged(it) }, // This needed to be Modify by ViewModel
                labelText = "My ID",
                onClick = { viewModel.onUserIdChanged(it) },

                isError = idError.isError,
                displayList = idList,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Password TextField
            NutriTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                labelText = "Password",

                isPassword = true,

                isError = passwordError.isError,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LoginStatusMessage(passwordError = passwordError, idError = idError)

            Spacer(modifier = Modifier.weight(1f))

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

            // Login Button
            NutriTrackButton(
                onClick = {
                    viewModel.login(
                        onSuccess = {
                            // Navigate to "Questionnaire" if no food intake exists
                            context.startActivity(Intent(context, QuestionnaireScreen::class.java))
                        },
                        onSuccessNext = {
                            // Navigate to "Home" if food intake exists
                            context.startActivity(Intent(context, Dashboard::class.java))
                        }
                    )
                },
            ) {
                Text(
                    text = "Login",
                    color = onPrimaryColor,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Register Button
            NutriTrackButton(
                modifier = Modifier.padding(bottom = 36.dp),
                onClick = { onDismiss; onRegisterClick() },
            ) {
                Text(
                    text = "Register",
                    color = onPrimaryColor,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

/**
 * This is the Login Status Message
 * @param phoneError is the phone error
 * @param idError is the id error
 * @param passwordError is the password error
 */
@Composable
fun LoginStatusMessage(
    phoneError: ValidationResult = ValidationResult(false),
    idError: ValidationResult = ValidationResult(false),
    passwordError: ValidationResult = ValidationResult(false),
    nameError: ValidationResult = ValidationResult(false)
) {
    when {
        idError.isError -> {
            LoginText(idError.message)
        }

        phoneError.isError -> {
            LoginText(phoneError.message)
        }

        passwordError.isError -> {
            LoginText(passwordError.message)
        }

        nameError.isError -> {
            LoginText(nameError.message)
        }

        else -> LoginText(
            "This app is only for pre-registered users. Please have your ID and your phone number ready before continuing.",
            isError = false
        )
    }
}

/**
 * This is the Login Text
 * @param text is the text to display
 * @param isError is the error state
 * @return the text
 */
@Composable
fun LoginText(text: String, isError: Boolean = true) {
    Text(
        text = text,
        color = if (isError) Color.Red else onSecondaryColor,
        fontStyle = FontStyle.Italic,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 4.dp)
    )
}
