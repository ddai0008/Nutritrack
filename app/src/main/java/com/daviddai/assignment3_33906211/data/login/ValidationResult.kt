package com.daviddai.assignment3_33906211.data.login

/**
 * This is the data class for the validation result.
 */
data class ValidationResult(
    val isError: Boolean = false,
    val message: String = ""
)