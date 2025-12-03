package com.daviddai.assignment3_33906211.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Insert new user into the database.
     */
    @Insert
    suspend fun insert(user: User)

    /**
     * Insert multiple users into the database.
     */
    @Insert
    suspend fun insertAllUsers(users: List<User>)

    /**
     * Update new user into the database.
     */
    @Update
    suspend fun update(user: User)

    /**
     * Update user name into the database.
     */
    @Query("UPDATE users SET name = :newName WHERE userId = :userId")
    suspend fun updateUserName(userId: String, newName: String)

    /**
     * Get all user ids from the database.
     */
    @Query("SELECT userId FROM users ORDER BY userId ASC")
    fun getAllUserIds(): Flow<List<String>>

    /**
     * Retrieve a user from the database.
     */
    @Query("SELECT * FROM users WHERE userId = :userId ORDER BY userId ASC")
    suspend fun getUserById(userId: String): User

    @Query("SELECT avg(totalScore) FROM users WHERE sex = :gender")
    suspend fun getAverageScoreByGender(gender: String): Double?

    /**
     * Retrieve all users from the database.
     */
    @Query("SELECT * FROM users ORDER BY userId ASC")
    fun getAllUsers(): Flow<List<User>>
}