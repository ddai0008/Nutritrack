package com.daviddai.assignment3_33906211.data.genAi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * This interface define the data access object (DAO) for the AiResponse entity.
 */
@Dao
interface AiResponseDao {

    /**
     * Insert new AI response into the database.
     */
    @Insert
    suspend fun insert(aiResponse: AiResponse)

    @Query("DELETE FROM ai_responses WHERE userId = :userId")
    suspend fun deleteByUserId(userId: String)

    /**
     * Get all AI responses from the database.
     */
    @Query("SELECT * FROM ai_responses WHERE userId = :userId ORDER BY responseId")
    fun getAiResponsesByUserId(userId: String): Flow<List<AiResponse>>?

}