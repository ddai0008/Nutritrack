package com.daviddai.assignment3_33906211.data.fruits

sealed interface SearchUiState {

    /**
     * Empty state when the screen is first shown
     */
    object Initial : SearchUiState

    /**
     * Still loading
     */
    object Loading : SearchUiState

    /**
     * Text has been generated
     */
    data class Success(val fruit: Fruit) : SearchUiState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String) : SearchUiState
}