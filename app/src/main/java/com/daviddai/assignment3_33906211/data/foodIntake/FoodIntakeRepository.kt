package com.daviddai.assignment3_33906211.data.foodIntake


import android.content.Context
import com.daviddai.assignment3_33906211.data.user.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * This is the repository class for the FoodIntake data.
 */
class FoodIntakeRepository(context: Context) {
    // Property to hold the FoodIntakeDao instance.
    private val foodIntakeDao: FoodIntakeDao = UserDatabase.getDatabase(context).foodIntakeDao()

    /**
     * Insert new food intake into the database.
     */
    suspend fun insert(foodIntake: FoodIntake) = withContext(Dispatchers.IO) {
        foodIntakeDao.insert(foodIntake)
    }

    /**
     * Update new food intake into the database.
     */
    suspend fun update(foodIntake: FoodIntake) = withContext(Dispatchers.IO) {
        foodIntakeDao.update(foodIntake)
    }

    /**
     * Get all food intake from the database.
     */
    fun getAllFoodIntake(): Flow<List<FoodIntake>> = foodIntakeDao.getAllFoodIntake()

    /**
     * Get food intake by user id from the database.
     */
    suspend fun getFoodIntakeByUserId(userId: String): FoodIntake? = withContext(Dispatchers.IO) {
        foodIntakeDao.getFoodIntakeByUserId(userId)
    }
}