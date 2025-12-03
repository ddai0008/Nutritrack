package com.daviddai.assignment3_33906211.data.user

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * This is the repository class for the User data.
 */
class UserRepository(private val context: Context) {

    // Property to hold the userDao instance.
    val userDao: UserDao = UserDatabase.getDatabase(context).userDao()

    /**
     * Insert new user into the database.
     */
    suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        userDao.insert(user)
    }

    /**
     * Update new user into the database.
     */
    suspend fun update(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user)
    }

    /**
     * Get all users from the database.
     */
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun updateUserName(userId: String, newName: String) = withContext(Dispatchers.IO) {
        userDao.updateUserName(userId, newName)
    }

    /**
     * Get user by ID from the database.
     */
    suspend fun getUserById(userId: String): User? = withContext(Dispatchers.IO) {
        userDao.getUserById(userId)
    }

    /**
     * Get all user ids from the database.
     */
    suspend fun getAllUserIds(): List<String> = withContext(Dispatchers.IO) {
        userDao.getAllUserIds().first()
    }

    /**
     * Get average score by gender from the database.
     */
    suspend fun getAverageScoreByGender(gender: String): Double? = withContext(Dispatchers.IO) {
        userDao.getAverageScoreByGender(gender)
    }

    /**
     * Load CSV to the database when the app is first opened.
     */
    suspend fun initialLoadCsv(context: Context) = withContext(Dispatchers.IO) {
        val users = csvToDatabase(context)
        userDao.insertAllUsers(users)
    }


    /**
     * CSV parsing, and insert to the database.
     */
    private suspend fun csvToDatabase(context: Context): List<User> {
        var result = emptyList<User>()

        withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open("patients.csv")
                val reader = BufferedReader(InputStreamReader(inputStream))

                reader.useLines { lines ->
                    // turns CSV rows to a list
                    val linesList = lines.toList()

                    // Get the Column Name
                    val column = linesList.first().split(",").map { it.trim() }

                    // Turns CSV rows to a list of maps
                    val detailMaps = linesList.drop(1).mapNotNull { line ->
                        val fields = line.split(",").map { it.trim() }
                        column.zip(fields).toMap()
                    }

                    // Return the user and its field based on its gender
                    result = calculatedHeifaScore(detailMaps)
                }
            } catch (e: Exception) {
                throw Exception("CSV loading failed: ${e.message}")
            }
        }

        return result
    }
}

/**
 * This function calculates the Heifa score for each user.
 * @param userDetails The list of user details.
 * @return The list of User objects.
 */
private fun calculatedHeifaScore(userDetails: List<Map<String, String>>): List<User> {
    val result = mutableListOf<User>()

    for (user in userDetails) {
        val gender = user["Sex"]

        result.add(
            User(
                // Core User Identifier
                userId = user["User_ID"]?.toString() ?: "-1",
                sex = gender.toString(),
                phoneNumber = user["PhoneNumber"]?.toString() ?: "0000000000",

                // Heifa Score
                totalScore = user["HEIFAtotalscore$gender"]?.toDouble() ?: -1.0,
                discretionary = user["DiscretionaryHEIFAscore$gender"]?.toDouble() ?: -1.0,
                vegetables = user["VegetablesHEIFAscore$gender"]?.toDouble() ?: -1.0,
                fruit = user["FruitHEIFAscore$gender"]?.toDouble() ?: -1.0,
                grainsAndCereals = user["GrainsandcerealsHEIFAscore$gender"]?.toDouble() ?: -1.0,
                wholeGrains = user["WholegrainsHEIFAscore$gender"]?.toDouble() ?: -1.0,
                meatAndAlternatives = user["MeatandalternativesHEIFAscore$gender"]?.toDouble()
                    ?: -1.0,
                dairyAndAlternatives = user["DairyandalternativesHEIFAscore$gender"]?.toDouble()
                    ?: -1.0,
                sodium = user["SodiumHEIFAscore$gender"]?.toDouble() ?: -1.0,
                alcohol = user["AlcoholHEIFAscore$gender"]?.toDouble() ?: -1.0,
                water = user["WaterHEIFAscore$gender"]?.toDouble() ?: -1.0,
                sugar = user["SugarHEIFAscore$gender"]?.toDouble() ?: -1.0,
                saturatedFat = user["SaturatedFatHEIFAscore$gender"]?.toDouble() ?: -1.0,
                unsaturatedFat = user["UnsaturatedFatHEIFAscore$gender"]?.toDouble() ?: -1.0,
            )
        )
    }

    return result
}