package com.daviddai.assignment3_33906211.data.fruits

/**
 * This is the Response Model for the FruityAPI
 */
data class Nutrition(
    val calories: Int,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
) {
    fun display() = "Calories: $calories, Sugar: $sugar, Carbohydrates: $carbohydrates, Protein: $protein"
}
