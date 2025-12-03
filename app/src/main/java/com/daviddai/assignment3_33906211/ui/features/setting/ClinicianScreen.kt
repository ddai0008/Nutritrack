package com.daviddai.assignment3_33906211.ui.features.setting


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FindInPage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daviddai.assignment3_33906211.data.genAi.ReportUiState
import com.daviddai.assignment3_33906211.data.viewModel.GenAiViewModel
import com.daviddai.assignment3_33906211.data.viewModel.ScoreViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriDivider
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.Shapes
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.mediumGray
import com.daviddai.assignment3_33906211.ui.theme.primaryColor


/**
 * Display the Composable for Clinician Screen.
 * @param innerPadding The inner padding for the screen.
 * @param viewModel The view model for the clinician Analysis
 */
@Composable
fun ClinicianScreen(
    innerPadding: PaddingValues,
    genAiViewModel: GenAiViewModel,
    scoreViewModel: ScoreViewModel
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val uiState = genAiViewModel.reportUiState.collectAsState().value

    Column(
        modifier = Modifier
            .widthIn(max = 600.dp)
            .padding(innerPadding)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Page Title
        ClinicianTitle()

        // Average Score Display
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AverageScoreDisplay(scoreViewModel.malesAverage)
            AverageScoreDisplay(scoreViewModel.femalesAverage)
        }

        NutriDivider(color = mediumGray)

        // Generate Pattern
        NutriTrackButton(
            onClick = { genAiViewModel.generatePattern(context) },
            buttonShape = Shapes.large
        ) {
            Text(
                text = "Find Data Pattern",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Outlined.FindInPage,
                contentDescription = "Generate Report"
            )
        }

        LazyColumn (
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                when (uiState) {
                    is ReportUiState.Success -> {
                        uiState.outputTexts.forEach { analysis ->
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .weight(1f)
                                    .shadow(elevation = 6.dp, shape = Shapes.large)
                                    .background(color = backgroundColor, shape = Shapes.large)
                                    .border(1.dp, color = primaryColor, shape = Shapes.large)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = analysis,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                    // If Loading, show loading animation
                    is ReportUiState.Loading -> {
                        CircularProgressIndicator(color = primaryColor)
                    }

                    // Display Error Message if Found
                    is ReportUiState.Error -> {
                        Text(
                            text = uiState.errorMessage,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    else -> null
                }
            }
        }
    }
}

/**
 * Display the Composable for Clinician Screen.
 */
@Composable
private fun ClinicianTitle() {
    NutriLargeTitle(
        title = "Clinician Dashbord",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun AverageScoreDisplay(
    score: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Average HEIFA Score (Males): $score",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}