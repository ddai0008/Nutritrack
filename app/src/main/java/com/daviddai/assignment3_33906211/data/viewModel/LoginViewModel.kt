package com.daviddai.assignment3_33906211.data.viewModel


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.data.login.AuthManager
import com.daviddai.assignment3_33906211.data.login.ValidationResult
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntakeRepository
import com.daviddai.assignment3_33906211.data.login.LoginsRepository
import com.daviddai.assignment3_33906211.data.user.User
import com.daviddai.assignment3_33906211.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


/**
 * ViewModel for the login screen.
 * Only responsible for managing login data
 * @param application is the Application
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Used
    private val userRepository: UserRepository = UserRepository(application.applicationContext)

    private val loginsRepository: LoginsRepository = LoginsRepository(application.applicationContext)

    private val foodIntakeRepository: FoodIntakeRepository = FoodIntakeRepository(application.applicationContext)

    // Class Constant Variable
    companion object {
        private const val CLINIC_KEY = "dollar-entry-apples"
        private const val MAX_CLINIC_ERROR_TRIALS = 5
        private const val SHARED_PREFS_NAME = "Current_user"
    }

    // UI State
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    // Form fields
    var clinicEntry by mutableStateOf("")
        private set

    var userID by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    // Validation errors
    var idError by mutableStateOf(ValidationResult())
        private set

    var passwordError by mutableStateOf(ValidationResult())
        private set

    var clinicError by mutableStateOf(ValidationResult())
        private set

    // Security state
    private var clinicErrorTrials = MAX_CLINIC_ERROR_TRIALS

    var isAccountLocked = false
        private  set

    // Load initial data
    init {
        refreshData()
    }

    // Getter and Setter for Textfield
    fun onClinicEntryChanged(value: String) {
        clinicEntry = value
    }

    fun onUserIdChanged(value: String) {
        userID = value
        idError = ValidationResult() // Clear error when typing
    }

    fun onPasswordChanged(value: String) {
        password = value
        passwordError = ValidationResult() // Clear error when typing
    }

    // Validation Mechanism to verify input
    fun validateClinicInput(): Boolean {
        try {
            clinicError = when {
                // If Blank display error
                clinicEntry.isBlank() -> ValidationResult(
                    isError = true,
                    message = "Password cannot be empty,  ${clinicErrorTrials} attempts remaining"
                )
                // if incorrect display error
                clinicEntry != CLINIC_KEY -> ValidationResult(
                    isError = true,
                    message = "Invalid password, ${clinicErrorTrials} attempts remaining"
                )

                else -> ValidationResult()
            }

            // If error happened, and no trials left, lock the login
            if (clinicError.isError && --clinicErrorTrials < 0) {
                viewModelScope.launch { handleAccountLockout() } // Launch coroutine here
            }

        } catch (e: Exception) {
            throw Exception("Failed to validate clinic input: ${e.message}")
        }

        // Return Result
        return !clinicError.isError
    }

    suspend fun validateUserInput(): Boolean {
        return withContext(Dispatchers.Main) {
            try {
                val login = loginsRepository.getLoginById(userID)

                // Validate the ID input
                idError = when {
                    userID.isBlank() -> ValidationResult(true, "User ID cannot be empty")
                    login == null -> ValidationResult(true, "User is not registered")
                    else -> ValidationResult()
                }

                // Validate password input
                passwordError = when {
                    password.isBlank() -> ValidationResult(true, "Password cannot be empty")
                    login == null || login.password != password -> ValidationResult(true, "Invalid password")
                    else -> ValidationResult()
                }

                // Return Result
                !idError.isError && !passwordError.isError

            } catch (e: Exception) {
                throw Exception("Failed to validate: ${e.message}")
            }
        }

    }

    private suspend fun handleAccountLockout() {
        // Locks the Account
        isAccountLocked = true
        clinicError = ValidationResult(
            isError = true,
            message = "Too many attempts. Please try again after 1 minute"
        )

        // Delay 1 minute
        delay(60000)

        // Reset the Variable
        clinicErrorTrials = MAX_CLINIC_ERROR_TRIALS
        isAccountLocked = false
        clinicError = ValidationResult()
    }

    // Main Login Function
    fun login(onSuccess: () -> Unit, onSuccessNext: () -> Unit) {
        viewModelScope.launch {
            try {
                // If no error, login
                if (validateUserInput()) {
                    val foodIntake = foodIntakeRepository.getFoodIntakeByUserId(userID)

                    AuthManager.login(userID)
                    saveLoginSharePref(getApplication(), userID) // Save for future Login

                    // if has filled the questionnaire, go to dashboard
                    if (foodIntake != null) onSuccessNext() else onSuccess()
                }
            } catch (e: Exception) {
                throw Exception("Login failed: ${e.message}")
            }
        }
    }

    // admin Login Function
    fun adminLogin(onSuccess: () -> Unit) {
        if (!validateClinicInput() || isAccountLocked) return
        onSuccess()
    }

    // Refresh the data
    fun refreshData() {
        viewModelScope.launch {
            try {
                userRepository.getAllUsers().collect { userList ->
                    _users.value = userList
                }
            } catch (e: Exception) {
                throw Exception("Failed to load users: ${e.message}")
            }
        }
    }

    // get share preference
    fun getSharePref(context: Context): String? {
        val sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("userID", null)
    }

    // save share preference for future login
    fun saveLoginSharePref(context: Context, userID: String) {
        val sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("userID", userID)
        editor.apply()
    }

    // check if user has logged in previously
    fun checkSharePrefExist(context: Context): Boolean {
        // Reference: https://stackoverflow.com/questions/22089411/how-to-get-all-keys-of-sharedpreferences-programmatically-in-android
        val sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return !sharedPref.all.isEmpty()
    }
}
