package com.daviddai.assignment3_33906211.data.login

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.daviddai.assignment3_33906211.data.user.User

/**
 * Entity representing a user login information
 * used to store user login information in the database
 * Foreign Key is the user_id from [USER]
 */
@Entity(
    tableName = "logins",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],  // Parent PK
            childColumns = ["userId"],  // Child PK (and FK)
            onDelete = ForeignKey.CASCADE  // Optional: Auto-delete profile if user is deleted
        )
    ]
)
data class Login(
    /**
     * The unique identifier for the user.
     */
    @PrimaryKey
    val userId: String,

    /**
     * The PhoneNumber for the user.
     */
    val phoneNumber: String,

    /**
     * The password use for login.
     */
    val password: String,
)
