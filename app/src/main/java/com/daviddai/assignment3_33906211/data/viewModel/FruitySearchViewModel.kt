package com.daviddai.assignment3_33906211.data.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.data.fruits.FruityAPI
import com.daviddai.assignment3_33906211.data.fruits.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FruitySearchViewModel(application: Application) : AndroidViewModel(application) {
    private val fruityAPI = FruityAPI.create()

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    var searchInput by mutableStateOf("")
        private set

    fun updateSearchInput(input: String) {
        searchInput = input
        clearResults()
    }

    fun search() {

        if (searchInput.isBlank()) {
            _searchUiState.value = SearchUiState.Error("Please enter a fruit name")
            return
        }

        viewModelScope.launch {
            _searchUiState.value = SearchUiState.Loading

            if (!isNetworkAvailable()) {
                _searchUiState.value =
                    SearchUiState.Error("No internet connection, Please Make your you are connected to internet")
                return@launch
            }

            try {
                val result = fruityAPI.getFruitByName(searchInput.trim().lowercase())

                if (result.isSuccessful) {
                    result.body()?.let { fruit ->
                        _searchUiState.value = SearchUiState.Success(fruit)
                    }
                } else {
                    _searchUiState.value = when (result.code()) {
                        404 -> SearchUiState.Error("Fruit '$searchInput' not found")
                        else -> SearchUiState.Error("API error: ${result.message()}")
                    }
                }
            } catch (e: Exception) {
                SearchUiState.Error("Network error: ${e.message}")
            }
        }
    }

    fun clearResults() {
        _searchUiState.value = SearchUiState.Initial
    }

    // Reference: Week 9 Lab
    fun isNetworkAvailable(): Boolean {
        val applicationContext = getApplication<Application>().applicationContext

        // Get the ConnectivityManager system service
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Check if the device has an active network
        val network = connectivityManager.activeNetwork ?: return false
        // Get the network capabilities for the active network
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        // Check if the network has any of the following transports:
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}