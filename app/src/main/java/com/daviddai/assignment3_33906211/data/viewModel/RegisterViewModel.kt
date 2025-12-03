package com.daviddai.assignment3_33906211.data.viewModel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.data.login.ValidationResult
import com.daviddai.assignment3_33906211.data.login.Login
import com.daviddai.assignment3_33906211.data.login.LoginsRepository
import com.daviddai.assignment3_33906211.data.user.User
import com.daviddai.assignment3_33906211.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This is the ViewModel for Register
 * Only responsible for managing Register
 * @param application is the Application
 */
class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Used
    private val userRepository: UserRepository = UserRepository(getApplication())

    private val loginsRepository: LoginsRepository = LoginsRepository(getApplication())

    // For displaying user data
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    // Form fields
    var userID by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordConfirm by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    // fields errors
    var idError by mutableStateOf(ValidationResult())
        private set

    var phoneError by mutableStateOf(ValidationResult())
        private set

    var passwordError by mutableStateOf(ValidationResult())
        private set

    var nameError by mutableStateOf(ValidationResult())
        private set

    // Load initial data
    init {
        loadInitialData()
    }

    // Load initial data from CSV
    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDb = userRepository.getAllUsers().first()

            // If the database is empty, load data from CSV
            if (currentDb.isEmpty()) {
                userRepository.initialLoadCsv(getApplication())
            }

            // Refresh the data
            refreshData()
        }
    }

    // Refresh the data
    fun refreshData() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

    // Setter for OutlineTextField Data
    fun onUserIdChanged(value: String) {
        userID = value
    }

    fun onPhoneNumberChanged(value: String) {
        phoneNumber = value
    }

    fun onPasswordChanged(value: String) {
        password = value
    }

    fun onPwConfirmChanged(value: String) {
        passwordConfirm = value
    }

    fun onNameChanged(value: String) {
        name = value
    }

    // validate userId and PhoneNumber
    suspend fun validateInput(): Boolean {
        return withContext(Dispatchers.IO) {
            val currentUsers = userRepository.getAllUsers().first()
            val existingLogin = loginsRepository.getLoginById(userID)

            // ID validation
            idError = when {
                userID.isBlank() -> ValidationResult(true, "User ID cannot be empty")
                currentUsers.none { it.userId == userID } -> ValidationResult(true, "User not found")
                existingLogin != null -> ValidationResult(true, "User already registered")
                else -> ValidationResult()
            }

            // Phone validation
            phoneError = when {
                !phoneNumber.matches(Regex("\\d{10,15}")) -> ValidationResult(
                    true,
                    "Invalid phone number format"
                )

                currentUsers.none { it.userId == userID && it.phoneNumber == phoneNumber } ->
                    ValidationResult(true, "Invalid phone number")

                else -> ValidationResult()
            }

            // Password validation
            passwordError = when {
                password.isBlank() || passwordConfirm.isBlank() -> ValidationResult(
                    true,
                    "Password fields cannot be empty"
                )

                password != passwordConfirm -> ValidationResult(true, "Passwords do not match")
                else -> ValidationResult()
            }

            // Name validation
            nameError = when {
                name.isBlank() -> ValidationResult(true, "Name cannot be empty")
                name.length < 2 -> ValidationResult(true, "Name must be at least 3 characters")
                !name.matches(Regex("^[a-zA-Z]+$")) -> ValidationResult(true, "Name must only contain letters")
                else -> ValidationResult()
            }

            !idError.isError && !phoneError.isError && !passwordError.isError && !nameError.isError
        }
    }

    // Register
    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            if (validateInput()) {
                // Insert new login
                loginsRepository.insert(Login(userID, phoneNumber, password))

                //update user name
                userRepository.updateUserName(userID, name)

                // show Success status and return to main menu
                Toast.makeText(getApplication(), "Register Successfully",Toast.LENGTH_SHORT).show()
                onSuccess()
            }
        }
    }
}
