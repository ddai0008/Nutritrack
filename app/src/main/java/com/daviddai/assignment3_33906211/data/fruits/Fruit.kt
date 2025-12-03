package com.daviddai.assignment3_33906211.data.fruits

/**
 * This is the Response Model for the FruityAPI
 */
data class Fruit(
    val name: String,
    val family: String,
    val nutritions: Nutrition
) {
    fun display() = "Name: $name, Family: $family, ${nutritions.display()}"
}
