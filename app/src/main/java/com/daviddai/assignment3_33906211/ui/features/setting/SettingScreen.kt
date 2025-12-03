package com.daviddai.assignment3_33906211.ui.features.setting

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daviddai.assignment3_33906211.data.viewModel.LoginViewModel
import com.daviddai.assignment3_33906211.data.viewModel.SettingViewModel
import com.daviddai.assignment3_33906211.ui.MainActivity
import com.daviddai.assignment3_33906211.ui.components.NutriDivider
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.theme.lightGray
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor
import kotlin.jvm.java

/**
 * Display the Composable for Setting Screen.
 * @param innerPadding The inner padding for the screen.
 * @param settingViewModel The view model for the setting screen.
 * @param loginViewModel The view model for the Admin Login screen.
 */
@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    settingViewModel: SettingViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val userState = settingViewModel.user.collectAsState()

    // Keep Track of display Composable
    var showClinicLogin by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .widthIn(max = 600.dp)
            .padding(innerPadding)
            .padding(vertical = 8.dp)
            .verticalScroll(scrollState)
    ) {
        SettingTitle()

        SettingBox(
            title = "ACCOUNT"
        ) {
            SettingItem(
                icon = Icons.Outlined.Person,
                text = userState.value?.name ?: "Guest",
            )

            SettingItem(
                icon = Icons.Outlined.Phone,
                text = settingViewModel.formatFixedPhoneNumber(userState.value?.phoneNumber.toString()),
            )

            SettingItem(
                icon = Icons.Outlined.AssignmentInd,
                text = userState.value?.userId ?: "- - -",
            )
        }

        NutriDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = lightGray
        )

        SettingBox(
            title = "OTHER SETTING"
        ) {
            SettingItem(
                icon = Icons.AutoMirrored.Outlined.Logout,
                text = "Logout",
                isClickable = true,
                onClick = {
                    settingViewModel.logout()
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            )

            SettingItem(
                icon = Icons.Outlined.Person,
                text = "Clinical Login",
                isClickable = true,
                onClick = {
                    showClinicLogin = true
                }
            )
        }

        // Handle Clinic Login display
        if (showClinicLogin) {
            ClinicialLogin(
                viewModel = loginViewModel,
                onDismiss = { showClinicLogin = false },
                navController = navController
            )
        }
    }
}

/**
 * Display the Composable for Setting .
 */
@Composable
private fun SettingTitle() {
    NutriLargeTitle(
        title = "Setting",
        style = MaterialTheme.typography.headlineSmall
    )
}

/**
 * Display the Composable for Setting Box.
 * @param title The title of the box.
 * @param content The content of the box.
 */
@Composable
private fun SettingBox(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        content()
    }
}

/**
 * Display the Composable for Setting Item.
 * @param icon The icon of the item.
 * @param text The text of the item.
 * @param isClickable Whether the item is clickable.
 * @param onClick The action to perform when the item is clicked.
 */
@Composable
private fun SettingItem(
    icon: ImageVector,
    text: String,
    isClickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = isClickable, // Only Allow If Clickable
                onClick = { onClick() }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = "Profile user",
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = onSecondaryColor,
            modifier = Modifier.weight(1f)
        )

        // If Clickable, display Arrow Icon
        if (isClickable) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "See all Scores",
                tint = Color.Gray,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}