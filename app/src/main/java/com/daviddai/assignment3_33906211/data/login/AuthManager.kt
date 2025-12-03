package com.daviddai.assignment3_33906211.data.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * This is the Authentication Manager
 * Keep track of the current logged in user
 * @property _userId The current logged in user
  */
object AuthManager {
    // This tracks the logged in user
    private val _userId: MutableState<String?> = mutableStateOf(null)

    // Login the user
    fun login(userId: String) {
        _userId.value = userId
    }

    // Logout the user
    fun logout() {
        _userId.value = null
    }

    // Getter for _
    fun getStudentId(): String? {
        return _userId.value
    }
}