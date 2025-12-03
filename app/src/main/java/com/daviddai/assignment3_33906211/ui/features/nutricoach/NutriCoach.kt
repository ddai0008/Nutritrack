package com.daviddai.assignment3_33906211.ui.features.nutricoach

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daviddai.assignment3_33906211.data.fruits.Fruit
import com.daviddai.assignment3_33906211.data.fruits.SearchUiState
import com.daviddai.assignment3_33906211.data.genAi.UiState
import com.daviddai.assignment3_33906211.data.viewModel.FruitySearchViewModel
import com.daviddai.assignment3_33906211.data.viewModel.GenAiViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriDivider
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTextField
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.errorColor
import com.daviddai.assignment3_33906211.ui.theme.lightGray
import com.daviddai.assignment3_33906211.ui.theme.mediumGray
import com.daviddai.assignment3_33906211.ui.theme.primaryColor

/**
 * Display the Composable for NutriCoach
 * @param innerPadding the padding applied to the content
 * @param genAiViewModel the view model for the NutriCoach screen
 */
@Composable
fun NutriCoach(
    innerPadding: PaddingValues,
    genAiViewModel: GenAiViewModel,
    fruitySearchViewModel: FruitySearchViewModel = viewModel()
) {
    val context = LocalContext.current

    val uiState = genAiViewModel.uiState.collectAsState()
    val searchUiState = fruitySearchViewModel.searchUiState.collectAsState().value

    // Get Response History
    val responses by genAiViewModel.aiResponse.collectAsState()

    // Reference: https://stackoverflow.com/questions/71199037/list-state-on-jetpack-compose
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()

    // Reference: https://stackoverflow.com/questions/64753944/orientation-on-jetpack-compose
    // Check the device orientation, where it is landscape or portrait
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // Scrollable content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            NutriCoachTitle()

            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    // Left Column
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        GenAiContent(responses, uiState.value, listState)
                    }

                    // Vertical Divider
                    VerticalDivider(
                        color = mediumGray,
                        thickness = 2.dp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    // Right Column (Conditional)
                    if (genAiViewModel.fruitScore < 5) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(horizontal = 16.dp)
                                .widthIn(min = 400.dp)
                                .verticalScroll(scrollState)
                        ) {
                            FruitContent(fruitySearchViewModel, searchUiState)
                        }
                    }
                }
            } else {

                if (genAiViewModel.fruitScore < 5) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        FruitContent(
                            fruitySearchViewModel,
                            searchUiState
                        )
                    }
                }


                NutriDivider(
                    modifier = Modifier,
                    color = mediumGray,
                    elevation = 6.dp
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 350.dp)
                ) {
                    GenAiContent(responses, uiState.value, listState)
                }
            }
        }

        // Fixed button at bottom-end
        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            GenAiButton(
                viewModel = genAiViewModel,
                context = context
            )
        }

        // Fixed button at bottom-start
        Box(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            ClearButton(
                viewModel = genAiViewModel,
            )
        }

    }

}

/**
 * Display the Composable for Title
 */
@Composable
private fun NutriCoachTitle() {
    NutriLargeTitle(
        title = "NutriCoach",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(16.dp)
    )
}

/**
 * Display the Composable for GenAi Button
 */
@Composable
private fun GenAiButton(
    viewModel: GenAiViewModel,
    context: Context,
) {
    NutriTrackButton(
        onClick = { viewModel.processPrompt(context) },
        buttonModifier = Modifier,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Generate Tip",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            Icons.AutoMirrored.Outlined.Message,
            contentDescription = "Generate Tip",
            Modifier.size(24.dp)
        )
    }
}

/**
 * Display the Composable for Clear
 */
@Composable
private fun ClearButton(
    viewModel: GenAiViewModel
) {
    NutriTrackButton(
        onClick = {
            viewModel.deleteByUserId()
        },
        buttonModifier = Modifier,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Clear",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            Icons.Outlined.Clear,
            contentDescription = "Clear",
            Modifier.size(24.dp)
        )
    }
}


