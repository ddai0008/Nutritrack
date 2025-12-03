package com.daviddai.assignment3_33906211.data.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.data.login.AuthManager
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntake
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntakeRepository
import com.daviddai.assignment3_33906211.data.login.LoginDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

/**
 * ViewModel for the Questionnaire screen.
 * Only manage Questionnaires data
 * @param application is the Application
 */
class QuestionnaireViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Used
    private val foodIntakeRepository = FoodIntakeRepository(getApplication())

    private val userId = AuthManager.getStudentId()

    private var existingFoodIntake: FoodIntake? = null

    // Question Answer
    var selectedList = mutableStateOf<List<String>>(listOf())
        private set

    var selectedPersona = mutableStateOf<String?>(null)
        private set

    var biggestMealTime: MutableState<String> = mutableStateOf("")
        private set

    var sleepTime: MutableState<String> = mutableStateOf("")
        private set

    var wakeUpTime: MutableState<String> = mutableStateOf("")
        private set

    // Error Message
    var errorMessage = mutableStateOf<String?>(null)
        private set

    // Initialize the viewModel and prefilled the data if exist
    init {
        viewModelScope.launch {
            existingFoodIntake = getExistingFoodIntake()

            if (existingFoodIntake != null) {
                selectedList.value = existingFoodIntake!!.foodCategory
                selectedPersona.value = existingFoodIntake!!.persona
                biggestMealTime.value = existingFoodIntake!!.biggestMealTime
                sleepTime.value = existingFoodIntake!!.sleepTime
                wakeUpTime.value = existingFoodIntake!!.wakeUpTime
            }
        }
    }

    // get the current user foodIntake if exist
    suspend fun getExistingFoodIntake(): FoodIntake? {
        return foodIntakeRepository.getFoodIntakeByUserId(userId.toString())
    }

    // Getter and Setter for Textfield
    fun onSelectedListChange(value: List<String>) {
        selectedList.value = value
    }

    fun onSelectedPersonaChange(value: String) {
        selectedPersona.value = value
    }

    // Validation Mechanism to verify times input
    fun checkTime(): Boolean {
        val biggestMeal = LocalTime.parse(biggestMealTime.value)
        val sleep = LocalTime.parse(sleepTime.value)
        val wakeUp = LocalTime.parse(wakeUpTime.value)

        val isTimeSame =
            biggestMealTime.value == sleepTime.value || biggestMealTime.value == wakeUpTime.value || sleepTime.value == wakeUpTime.value

        val isTimeInvalid = when {
            // When the sleep time and wake up time is same day e.g. sleep at 11am and wakeup at 10pm
            sleep < wakeUp -> {
                biggestMeal > sleep && biggestMeal < wakeUp
            }

            // When the sleep time and wake up time is different day e.g. sleep at 11pm and wakeup at 10am
            sleep > wakeUp -> {
                biggestMeal > sleep || biggestMeal < wakeUp
            }

            else -> false
        }

        errorMessage.value = when {
            isTimeSame -> "Meal time cannot be the same with sleep time or wake up time"
            isTimeInvalid -> "Meal time is invalid, You cannot eat during sleeping"
            else -> null
        }

        // Return Result
        return !isTimeSame && !isTimeInvalid
    }

    // Remove the error message when change values
    fun removeErrorMessage() {
        errorMessage.value = null
    }

    // Enable Button if the condition is met
    fun checkButtonEligibility(index: Int): Boolean {
        val isTimeEmpty =
            biggestMealTime.value.isEmpty() || sleepTime.value.isEmpty() || wakeUpTime.value.isEmpty()

        return when (index) {
            0 -> selectedList.value.isNotEmpty()
            1 -> selectedPersona.value != null
            2 -> !isTimeEmpty
            else -> true

        }
    }

    // Save the questionnaire Answer in DB
    fun saveQuestionnaire() {
        viewModelScope.launch(Dispatchers.IO) {
            val answer = FoodIntake(
                userId = AuthManager.getStudentId().toString(),
                foodCategory = selectedList.value,
                persona = selectedPersona.value ?: "",
                biggestMealTime = biggestMealTime.value,
                sleepTime = sleepTime.value,
                wakeUpTime = wakeUpTime.value
            )

            upsert(answer)
        }
    }

    // If FoodIntake exist, update it, else insert it
    suspend fun upsert(foodIntake: FoodIntake) {
        if (existingFoodIntake != null) {
            foodIntakeRepository.update(foodIntake)
        } else {
            foodIntakeRepository.insert(foodIntake)
        }
    }

}