package com.daviddai.assignment3_33906211.data.foodIntake


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.daviddai.assignment3_33906211.data.RoomTypeConverters
import com.daviddai.assignment3_33906211.data.user.User


/**
 * FoodIntake Entity
 * used to store user questionnaire information in the database
 * Foreign Key is the user_id from [USER]
 */
@Entity(
    tableName = "food_intake",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],  // Parent PK
            childColumns = ["userId"],  // Child PK (and FK)
            onDelete = ForeignKey.CASCADE  // Optional: Auto-delete profile if user is deleted
        )
    ]
)
@TypeConverters(RoomTypeConverters::class)
data class FoodIntake(
    /**
     * The unique identifier for the user.
     */
    @PrimaryKey
    val userId: String,

    /**
     * The food category selected by the user
     */
    val foodCategory: List<String>,

    /**
     * The persona selected by the user
     */
    val persona: String,

    /**
     * The time of the biggest meal selected by the user
     */
    val biggestMealTime: String,

    /**
     * The time of the sleep selected by the user
     */
    val sleepTime: String,

    /**
     * The time of the wake up selected by the user
     */
    val wakeUpTime: String
) {
    fun display() = "Food They Eat Daily: $foodCategory"
}
