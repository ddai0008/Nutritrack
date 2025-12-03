package com.daviddai.assignment3_33906211.ui.features.nutricoach

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.data.fruits.Fruit
import com.daviddai.assignment3_33906211.data.fruits.SearchUiState
import com.daviddai.assignment3_33906211.data.viewModel.FruitySearchViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriTextField
import com.daviddai.assignment3_33906211.ui.theme.errorColor
import com.daviddai.assignment3_33906211.ui.theme.primaryColor

@Composable
fun FruitContent(
    viewModel: FruitySearchViewModel,
    uiState: SearchUiState
) {
    Text(
        text = "Search Fruit",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )

    NutriTextField(
        value = viewModel.searchInput,
        onValueChange = { viewModel.updateSearchInput(it) },
        labelText = "Fruit Name",

        hasDropdown = true,
        expandClick = { viewModel.search() },
        icon = Icons.Outlined.Search
    )



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is SearchUiState.Loading -> {
                CircularProgressIndicator(color = primaryColor)
            }

            is SearchUiState.Success -> {
                val fruit = uiState.fruit

                FruitDisplay(fruit)
            }

            is SearchUiState.Error -> {
                Text(
                    text = uiState.errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = errorColor
                )
            }

            else -> null
        }
    }
}

/**
 * Display the Composable for Fruit
 * @param fruit the fruit to display
 */
@Composable
private fun FruitDisplay( fruit: Fruit ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchRow(
            title1 = "Name",
            title2 = "Family",
            fruitVarOne = fruit.name,
            fruitVarTwo = fruit.family
        )

        SearchRow(
            title1 = "carbohydrates",
            title2 = "protein",
            fruitVarOne = fruit.nutritions.carbohydrates.toString(),
            fruitVarTwo = fruit.nutritions.protein.toString()
        )

        SearchRow(
            title1 = "fat",
            title2 = "calories",
            fruitVarOne = fruit.nutritions.fat.toString(),
            fruitVarTwo = fruit.nutritions.calories.toString()
        )
    }
}

/**
 * Display the Composable for Search Row
 * @param fruitVarOne the first variable to display
 * @param fruitVarTwo the second variable to display
 */
@Composable
private fun SearchRow(
    title1: String,
    title2: String,
    fruitVarOne: String,
    fruitVarTwo: String
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$title1 : $fruitVarOne",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "$title2: $fruitVarTwo",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}