package com.daviddai.assignment3_33906211.ui.features.nutricoach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.daviddai.assignment3_33906211.data.genAi.AiResponse
import com.daviddai.assignment3_33906211.data.genAi.UiState
import com.daviddai.assignment3_33906211.ui.theme.primaryColor
import com.daviddai.assignment3_33906211.ui.theme.surfaceVariant
import kotlin.collections.isNotEmpty

/**
 * Display the Composable for GenAi Content
 * @param responses the list of AI responses
 * @param uiState the UI state of the AI
 * @param listState the state of the LazyColumn
 */
@Composable
fun GenAiContent(
    responses: List<AiResponse>,
    uiState: UiState,
    listState: LazyListState
) {
    LazyColumn (
        state = listState,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        // Display each AI response
        items(responses) { response ->
            GenAiItems {
                Text(
                    text = response.response,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Adding Loading Animation and TextBox
        item {
            when (uiState) {
                is UiState.Loading -> {
                    GenAiItems {
                        CircularProgressIndicator(color = primaryColor)
                    }
                }

                is UiState.Error -> {
                    GenAiItems {
                        Text(
                            text = uiState.errorMessage,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                else -> null
            }
        }
    }

    // Auto-scroll when new items are added
    LaunchedEffect(responses.size) {
        if (responses.isNotEmpty()) {
            listState.animateScrollToItem(responses.size)
        }
    }

    // Auto-scroll when Loading
    LaunchedEffect(uiState) {
        if (uiState is UiState.Loading) {
            listState.animateScrollToItem(responses.size)
        }
    }
}
