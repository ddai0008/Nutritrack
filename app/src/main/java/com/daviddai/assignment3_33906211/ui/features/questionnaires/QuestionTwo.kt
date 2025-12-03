package com.daviddai.assignment3_33906211.ui.features.questionnaires


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.ui.components.NutriDivider
import com.daviddai.assignment3_33906211.ui.components.NutriDropDownInput
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.components.PersonaDialog
import com.daviddai.assignment3_33906211.ui.features.questionnaires.categories.Persona
import com.daviddai.assignment3_33906211.ui.features.questionnaires.categories.personas

/**
 * This is the Composable for Question Two
 * @return a Composable for Question Two
 */
@Composable
fun QuestionTwo(
    selectedPersona: String?,
    newPersona: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add a back button to navigate back to the previous screen
        Text(
            text = "Persona",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.persona_background),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                bottom = (32.dp)
            )
        )

        PersonaSelectionGrid(personas) // personas is from Data.kt

        NutriDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = Color(0xFFB3B3B3)
        )

        Text(
            text = "Which persona best fits you?",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        NutriDropDownInput(
            value = selectedPersona?: "",
            labelText = "Persona",
            onClick = { newPersona(it.name) },

            displayList = personas,
            readOnly = true,
            itemName = { it.name })

    }
}

@Composable
fun PersonaSelectionGrid(personas: List<Persona>) {
    var selectedPersona by remember { mutableStateOf<Persona?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp)
    ) {
        items(personas) { persona ->
            NutriTrackButton(
                onClick = {
                    selectedPersona = persona
                }) {
                Text(
                    text = persona.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }

    // Reference: https://stackoverflow.com/questions/58606651/what-is-the-purpose-of-let-keyword-in-kotlin
    selectedPersona?.let { persona ->
        PersonaDialog(
            persona = persona, onDismiss = { selectedPersona = null })
    }

}