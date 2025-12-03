package com.daviddai.assignment3_33906211.data.viewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.data.login.AuthManager
import com.daviddai.assignment3_33906211.data.user.User
import com.daviddai.assignment3_33906211.data.user.UserRepository
import com.daviddai.assignment3_33906211.ui.features.insightAnalysis.HeifaScore
import com.daviddai.assignment3_33906211.ui.theme.errorColor
import com.daviddai.assignment3_33906211.ui.theme.scoreColor
import com.daviddai.assignment3_33906211.ui.theme.warningColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This is the view model for the Score screen.
 * Only manage the display of scores
 * @param application The application context.
 */
class ScoreViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Used
    private val userRepository: UserRepository = UserRepository(getApplication())

    // Get the current userId
    private val userId = AuthManager.getStudentId()

    var malesAverage by mutableStateOf("")
        private set

    var femalesAverage by mutableStateOf("")
        private set

    // Property to hold the user data for UI display
    var existingUser: User? = null
        private set

    // Property to hold the scores data for UI display
    var scores: List<HeifaScore> = emptyList()
        private set

    // Initialize the view model by fetching the user and score data
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getExistingUser()
            getScore()

            malesAverage = getAverageScoreByGender("Male").toString()
            femalesAverage = getAverageScoreByGender("Female").toString()
        }
    }

    // Fetch the user data from the repository
    suspend fun getExistingUser() {
        withContext(Dispatchers.IO) {
            val user = userRepository.getUserById(userId.toString())
            existingUser = user // Safe to assign null if repository returns null
        }
    }

    // Fetch the score data from the repository
    fun getScore() {
        viewModelScope.launch(Dispatchers.IO) {

            val user = existingUser ?: return@launch

            try {
                scores = listOf(
                    HeifaScore("discretionary", user.discretionary, 10.0),
                    HeifaScore("vegetables", user.vegetables, 5.0),
                    HeifaScore("fruit", user.fruit, 5.0),
                    HeifaScore("grains & Cereals", user.grainsAndCereals, 5.0),
                    HeifaScore("whole Grains", user.wholeGrains, 10.0),
                    HeifaScore("meat & Alternatives", user.meatAndAlternatives, 10.0),
                    HeifaScore("dairy & Alternatives", user.dairyAndAlternatives, 10.0),
                    HeifaScore("sodium", user.sodium, 10.0),
                    HeifaScore("alcohol", user.alcohol, 5.0),
                    HeifaScore("water", user.water, 5.0),
                    HeifaScore("sugar", user.sugar, 10.0),
                    HeifaScore("saturated Fat", user.saturatedFat, 5.0),
                    HeifaScore("unsaturated Fat", user.unsaturatedFat, 10.0)
                )
            } catch (e: Exception) {
                throw Exception("Failed to get score")
            }

        }
    }

    suspend fun getAverageScoreByGender(gender: String): Double? {
        return withContext(Dispatchers.IO) {
            userRepository.getAverageScoreByGender(gender)
        }
    }

    // UI color update
    fun scoreColor(score: Float, maximum: Float = 100f): Color {
        // when score is low, use unique color
        return when {
            score < maximum/3 -> errorColor
            score < maximum/2 -> warningColor
            else -> scoreColor
        }
    }
}