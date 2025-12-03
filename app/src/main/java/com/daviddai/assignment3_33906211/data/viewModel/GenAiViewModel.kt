package com.daviddai.assignment3_33906211.data.viewModel


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.BuildConfig
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntake
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntakeRepository
import com.daviddai.assignment3_33906211.data.genAi.AiResponse
import com.daviddai.assignment3_33906211.data.genAi.AiResponseRepository
import com.daviddai.assignment3_33906211.data.genAi.ReportUiState
import com.daviddai.assignment3_33906211.data.genAi.UiState
import com.daviddai.assignment3_33906211.data.login.AuthManager
import com.daviddai.assignment3_33906211.data.user.User
import com.daviddai.assignment3_33906211.data.user.UserRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A view model for the NutriCoach screen.
 * @param application The application
 * @constructor Creates a new NutriCoachViewModel
 */
class GenAiViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Used
    private val userRepository: UserRepository = UserRepository(getApplication())

    private val foodIntakeRepository = FoodIntakeRepository(getApplication())

    private val aiResponseRepository = AiResponseRepository(getApplication())

    // Current Logged in user
    private val userId = AuthManager.getStudentId()

    // Food Score for Conditional Display
    var fruitScore by mutableDoubleStateOf(0.0)
        private set

    // State of Response
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // State of Response
    private val _reportUiState = MutableStateFlow<ReportUiState>(ReportUiState.Initial)
    val reportUiState: StateFlow<ReportUiState> = _reportUiState.asStateFlow()

    // History of Response
    private val _aiResponse = MutableStateFlow<List<AiResponse>>(emptyList())
    val aiResponse: StateFlow<List<AiResponse>> = _aiResponse.asStateFlow()

    // Reference: Workshop 7
    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash", apiKey = "GOOGLE_API_KEY"
        )
    }

    // Refresh Data for display
    init {
        refreshData()

        viewModelScope.launch(Dispatchers.IO) {
            fruitScore = getScores()?.fruit ?: 0.0
        }
    }

    // Get user food intake info
    private suspend fun getFoodIntake(): FoodIntake? {
        return withContext(Dispatchers.IO) {
            foodIntakeRepository.getFoodIntakeByUserId(userId.toString())
        }
    }

    // Get user score
    suspend fun getScores(): User? {
        return withContext(Dispatchers.IO) {
            userRepository.getUserById(userId.toString())
        }
    }

    suspend fun getAllScores(): Flow<List<User>> {
        return withContext(Dispatchers.IO) {
            userRepository.getAllUsers()
        }
    }

    // Delete Response by UserId
    fun deleteByUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            aiResponseRepository.deleteByUserId(userId.toString())
            refreshData()
        }
    }

    // Generate three Interesting Pattern
    fun generatePattern(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _reportUiState.value = ReportUiState.Loading

            val allScores = getAllScores().first()

            val prompt = "Patient detail: $allScores" + context.getString(R.string.GenAi_Pattern_Prompt)

            val analyses = mutableListOf<String>()

            try {
                if (!isNetworkAvailable()) {
                    _reportUiState.value =
                        ReportUiState.Error("No internet connection, Please Make your you are connected to internet")
                    return@launch
                }

                var i = 0
                while (i < 3) {
                    val response = generativeModel.generateContent(content { text(prompt) })

                    response.text?.let { outputContent ->
                        analyses.add(outputContent)
                    }

                    i++
                }

                _reportUiState.value = ReportUiState.Success(analyses)

            } catch (e: Exception) {
                _reportUiState.value = ReportUiState.Error(e.message ?: "")
            }
        }
    }

    // Generate a fun Tips
    fun processPrompt(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading

            val foodIntake = getFoodIntake()?.display()
            val scores = getScores()?.display()

            // Reference: https://stackoverflow.com/questions/47628646/how-should-i-get-resourcesr-string-in-viewmodel-in-android-mvvm-and-databindi
            val prompt =
                "Patient detail: $foodIntake, Food Quality Score: $scores" + context.getString(R.string.GenAi_Prompt)

            try {
                if (!isNetworkAvailable()) {
                    _uiState.value =
                        UiState.Error("No internet connection, Please Make your you are connected to internet")
                    return@launch
                }

                val response = generativeModel.generateContent(content { text(prompt) })

                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                    aiResponseRepository.insert(
                        AiResponse(
                            userId = userId.toString(), response = outputContent
                        )
                    )

                    refreshData()

                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "")
            }
        }
    }

    // Update Response History
    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = aiResponseRepository.getAiResponsesByUserId(userId.toString())
                    ?.first() // Collect the first emitted list

                withContext(Dispatchers.Main) {
                    _aiResponse.value = result ?: emptyList()
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

    // Reference: Week 9 Lab
    // Checking internet
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
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
