package com.daviddai.assignment3_33906211.data.foodIntake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * This interface define the data access object (DAO) for the FoodIntake entity.
 */
@Dao
interface FoodIntakeDao {

    /**
     * Insert new food intake into the database.
     */
    @Insert
    suspend fun insert(foodIntake: FoodIntake)

    /**
     * Update new food intake into the database.
     */
    @Update
    suspend fun update(foodIntake: FoodIntake)

    /**
     * Get all food intake from the database.
     */
    @Query("SELECT * FROM food_intake ORDER BY userId ASC")
    fun getAllFoodIntake(): Flow<List<FoodIntake>>

    /**
     * Get all food intake from the database.
     */
    @Query("SELECT * FROM food_intake WHERE userId = :userId")
    suspend fun getFoodIntakeByUserId(userId: String): FoodIntake?
}