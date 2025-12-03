package com.daviddai.assignment3_33906211.data.genAi

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.daviddai.assignment3_33906211.data.user.User

/**
 * AiResponse Entity
 * used to store AI Response information in the database
 */
@Entity(
    tableName = "ai_responses",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],  // Parent PK
            childColumns = ["userId"],  // Child PK (and FK)
            onDelete = ForeignKey.CASCADE  // Optional: Auto-delete profile if user is deleted
        )
    ]
)
data class AiResponse(
    /**
     * The unique identifier for the Response.
     */
    @PrimaryKey(autoGenerate = true)
    val responseId: Int = 0,

    /**
     * The unique identifier for the user.
     */
    val userId: String,

    /**
     * The Response from the AI.
     */
    val response: String
)
