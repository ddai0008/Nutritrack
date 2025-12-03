package com.daviddai.assignment3_33906211.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a user information
 * used to store user information in the database
 */
@Entity(tableName = "users")
data class User(
    /**
     * The unique identifier for the user.
     */
    @PrimaryKey
    val userId: String,

    /**
     * The name of the user.
     */
    val name: String? = null,

    /**
     * The PhoneNumber for the user.
     */
    val phoneNumber: String,

    /**
     * The sex of the user.
     */
    val sex: String,

    /**
     * The Column below are the foods score for the user
     */
    val totalScore: Double,

    val discretionary: Double,

    val vegetables: Double,

    val fruit: Double,

    val grainsAndCereals: Double,

    val wholeGrains: Double,

    val meatAndAlternatives: Double,

    val dairyAndAlternatives: Double,

    val sodium: Double,

    val alcohol: Double,

    val water: Double,

    val sugar: Double,

    val saturatedFat: Double,

    val unsaturatedFat: Double
) {
    fun display() =
        "Name: $name, Sex: $sex, discretionary: $discretionary/10, veg: $vegetables/5, fruit: $fruit/5, " +
        "grains: $grainsAndCereals/5, whole: $wholeGrains/10, meat: $meatAndAlternatives/10, dairy: $dairyAndAlternatives/10 " +
        "sodium: $sodium/10, alcohol: $alcohol/5, water: $water/5, sugar: $sugar/10, saturated: $saturatedFat/5, " +
        "unsaturated: $unsaturatedFat/10"
}
