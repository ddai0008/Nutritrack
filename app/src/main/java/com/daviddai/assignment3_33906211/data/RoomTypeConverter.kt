package com.daviddai.assignment3_33906211.data


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This is the type converter for the Room database.
 * It is used to convert the list of string to string and vice versa.
 */
// Reference: https://stackoverflow.com/questions/73446959/how-to-store-a-list-field-in-android-room
class RoomTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun toString(stringList: List<String>?): String? {
        return gson.toJson(stringList)
    }

    // Reference: https://stackoverflow.com/questions/48853750/kotlin-convert-json-array-to-model-list-using-gson/52965655
    @TypeConverter
    fun toList(str: String?): List<String>? {
        if (str == null || str.isEmpty()) return listOf()

        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(str, type)
    }
}