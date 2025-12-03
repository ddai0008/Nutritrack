package com.daviddai.assignment3_33906211.ui.features.questionnaires

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor
import com.daviddai.assignment3_33906211.ui.theme.secondaryColor

// Reference Workshop 2
/**
 * This is the Composable for Questionnaire Top Bar
 * @return a Composable for Questionnaire Top Bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireTopBar() {
    // Handle return button
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = secondaryColor,
            titleContentColor = onSecondaryColor,
        ),
        title = {
            Text(
                "Food Intake Questionnaire",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, //truncate the text if it exceeds the available space.
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    // It takes the current activity out of the back stack and shows the previous activity.
                    onBackPressedDispatcher?.onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    tint = onSecondaryColor // tint function add color to it
                )
            }
        }
    )
}