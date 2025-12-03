package com.daviddai.assignment3_33906211.data.login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * This interface define the data access object (DAO) for the Login entity.
 */
@Dao
interface LoginDao {
    /**
     * Insert new login into the database.
     */
    @Insert
    suspend fun insert(login: Login)

    /**
     * Update new login into the database.
     */
    @Update
    suspend fun update(login: Login)

    /**
     * Delete login from the database.
     */
    @Delete
    suspend fun delete(login: Login)

    /**
     * Retrieve login from the database using its ID.
     */
    @Query("SELECT * FROM logins WHERE userId = :userId")
    suspend fun getLoginById(userId: String): Login?

    /**
     * Delete login from the database using its ID.
     */
    @Query("DELETE FROM logins WHERE userId = :userId")
    suspend fun deleteLoginById(userId: String)

    /**
     * Retrieve all logins from the database.
     */
    @Query("SELECT * FROM logins ORDER BY userId ASC")
    fun getAllLogins(): Flow<List<Login>>
}