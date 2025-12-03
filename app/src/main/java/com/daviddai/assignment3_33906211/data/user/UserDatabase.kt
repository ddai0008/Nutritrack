package com.daviddai.assignment3_33906211.data.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daviddai.assignment3_33906211.data.RoomTypeConverters
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntake
import com.daviddai.assignment3_33906211.data.foodIntake.FoodIntakeDao
import com.daviddai.assignment3_33906211.data.genAi.AiResponse
import com.daviddai.assignment3_33906211.data.genAi.AiResponseDao
import com.daviddai.assignment3_33906211.data.login.Login
import com.daviddai.assignment3_33906211.data.login.LoginDao
import kotlin.jvm.java

/**
 * This is the database class for the application. It is a Room database.
 * It contains one entity: [User].
 * The version is 1 and exportSchema is false.
 */
@Database(
    entities = [User::class, Login::class, FoodIntake::class, AiResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class UserDatabase : RoomDatabase() {

    /**
     * Returns the [userDao] object.
     * This is an abstract function that is implemented by Room.
     */
    abstract fun userDao(): UserDao

    /**
     * Returns the [loginDao] object.
     * This is an abstract function that is implemented by Room.
     */
    abstract fun loginDao(): LoginDao

    /**
     * Returns the [foodIntakeDao] object.
     * This is an abstract function that is implemented by Room.
     */
    abstract fun foodIntakeDao(): FoodIntakeDao

    /**
     * Returns the [aiResponseDao] object.
     * This is an abstract function that is implemented by Room.
     */
    abstract fun aiResponseDao(): AiResponseDao

    companion object {
        /**
         * This is a volatile variable that holds the database instance.
         * It is volatile so that it is immediately visible to all threads.
         */
        @Volatile
        private var Instance: UserDatabase? = null

        /**
         * Returns the database instance.
         * If the instance is null, it creates a new database instance.
         * @param context The context of the application.
         */
        fun getDatabase(context: Context): UserDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    "user_database"
                ).build().also { Instance = it; }
            }
        }
    }
}

