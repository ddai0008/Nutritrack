package com.daviddai.assignment3_33906211.data.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daviddai.assignment3_33906211.data.login.AuthManager
import com.daviddai.assignment3_33906211.data.user.User
import com.daviddai.assignment3_33906211.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.content.edit

/**
 * This is the view model for the Setting screen.
 * Only manage the setting of the app
 * @param application The application context.
 */
class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(application)

    // Class Constant Variable
    companion object {
        private const val SHARED_PREFS_NAME = "Current_user"
    }

    // property to hold the user data for UI display
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    // Initialize the view model by fetching the user data
    init {
        viewModelScope.launch {
            loadUser()
        }
    }

    // Fetch the user data from the repository
    private suspend fun loadUser() {
        val userId = AuthManager.getStudentId()
        val fetchedUser = getUserById(userId.toString())
        _user.value = fetchedUser
    }

    // Dao Access to fetch user by Id
    private suspend fun getUserById(userId: String): User? =
        withContext(Dispatchers.IO) {
            userRepository.getUserById(userId)
        }

    // Logout the user
    fun logout() {
        AuthManager.logout() // Clear the login status
        removeSharePreference(getApplication()) // Remove share preference
    }

    // Format the phone number to xxx-xxx-xxxx
    fun formatFixedPhoneNumber(phone: String): String {
        if (phone.length != 10) return phone //  If the phone number is invalid, return it as is

        return "${phone.substring(0, 3)}-${phone.substring(3, 6)}-${phone.substring(6, 10)}"
    }

    // Remove share preference
    fun removeSharePreference(context: Context) {
        val sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPref.edit() { clear() }
    }
}