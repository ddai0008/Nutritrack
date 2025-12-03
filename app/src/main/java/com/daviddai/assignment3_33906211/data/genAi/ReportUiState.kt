package com.daviddai.assignment3_33906211.data.genAi

/**
 * A sealed hierarchy describing the state of the text generation.
 */
sealed interface ReportUiState {
    /**
     * Empty state when the screen is first shown
     */
    object Initial : ReportUiState

    /**
     * Still loading
     */
    object Loading : ReportUiState

    /**
     * Text has been generated
     */
    data class Success(val outputTexts: List<String>) : ReportUiState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String) : ReportUiState
}