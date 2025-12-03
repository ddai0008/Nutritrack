package com.daviddai.assignment3_33906211.ui.features.login


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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.data.viewModel.RegisterViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriDropDownInput
import com.daviddai.assignment3_33906211.ui.components.NutriFullDialog
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTextField
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor

/**
 * This is the Register Dialog
 * @param viewModel is the ViewModel
 * @param onDismiss is the on dismiss request
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDialog(
    viewModel: RegisterViewModel,
    onDismiss: () -> Unit
) {
    val users by viewModel.users.collectAsState()
    var idList = users.map { it.userId }

    // Track Error
    val phoneError = viewModel.phoneError
    val idError = viewModel.idError
    val passwordError = viewModel.passwordError
    val nameError = viewModel.nameError

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
                title = "Register",
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

            // Phone Number TextField
            NutriTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChanged(it) },
                labelText = "Phone Number",

                isError = phoneError.isError,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Password TextField
            NutriTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                labelText = "Password",

                isError = passwordError.isError,
                isPassword = true,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Password Confirm TextField
            NutriTextField(
                value = viewModel.passwordConfirm,
                onValueChange = { viewModel.onPwConfirmChanged(it) },
                labelText = "Password",

                isError = passwordError.isError,
                isPassword = true,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Name TextField
            NutriTextField(
                value = viewModel.name,
                onValueChange = { viewModel.onNameChanged(it) },
                labelText = "Preferred Name",

                isError = nameError.isError,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Error Message Display
            LoginStatusMessage(phoneError, idError, passwordError, nameError)

            // Push Button to Bottom
            Spacer(modifier = Modifier.weight(1f))

            // Return
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

            // Register Button
            NutriTrackButton(
                modifier = Modifier.padding(bottom = 36.dp),
                onClick = {
                    viewModel.register {
                        onDismiss()
                    }
                },
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

