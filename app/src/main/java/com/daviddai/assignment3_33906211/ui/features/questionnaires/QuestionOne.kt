package com.daviddai.assignment3_33906211.ui.features.questionnaires

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.ui.features.questionnaires.categories.foodCategory
import com.daviddai.assignment3_33906211.ui.theme.primaryColor

/**
 * This is the Composable for Question One
 * @return a Composable for Question One
 * @param selectedOptions is the list of selected options
 */
@Composable
fun QuestionOne(
    selectedOptions: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Question
        Text(
            text = "Tick all the categories you can eat?",
            style = MaterialTheme.typography.titleLarge,
        )

        // Create a options for each categories
        foodCategory.forEach { category -> // Food categories is loaded from Data.kt
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    colors = CheckboxDefaults.colors(checkedColor = primaryColor,),
                    checked = category in selectedOptions,
                    onCheckedChange = {
                        // If exist, remove, else add
                        val newSelection =
                            if (category in selectedOptions) selectedOptions - category else selectedOptions + category
                        onSelectionChange(newSelection)
                    }
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}