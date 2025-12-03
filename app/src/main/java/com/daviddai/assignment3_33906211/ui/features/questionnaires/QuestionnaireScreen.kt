package com.daviddai.assignment3_33906211.ui.features.questionnaires


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.daviddai.assignment3_33906211.data.viewModel.QuestionnaireViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.features.dashboard.Dashboard
import com.daviddai.assignment3_33906211.ui.theme.NutriTrackTheme
import com.daviddai.assignment3_33906211.ui.theme.primaryColor
import kotlin.jvm.java

/**
 * This is the Composable for Questionnaire Screen
 * @return a Composable for Questionnaire Screen
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
class QuestionnaireScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewModel
        val viewModel: QuestionnaireViewModel by viewModels()

        setContent {
            NutriTrackTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { QuestionnaireTopBar() }
                ) { innerPadding ->
                    // Questionnaire Content
                    QuestionnaireContent(innerPadding, viewModel)
                }
            }
        }
    }
}

/**
 * This is the Composable for Questionnaire Content
 * @return a Composable for Questionnaire Content
 */
@Composable
private fun QuestionnaireContent(
    innerPadding: PaddingValues,
    viewModel: QuestionnaireViewModel
) {
    var currentQuestionIndex by rememberSaveable { mutableIntStateOf(0) }
    // Default at 33%
    val progress = (currentQuestionIndex + 1).toFloat() / 3


    // Storage the Answer
    val selectedOptions by viewModel.selectedList
    val selectedPersona by viewModel.selectedPersona

    val biggestMealTime = viewModel.biggestMealTime
    val sleepTime = viewModel.sleepTime
    val wakeUpTime = viewModel.wakeUpTime

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Divider for Decoration purposes
        HorizontalDivider(thickness = 2.dp, color = Color(0xFFB3B3B3))

        //Shows the Progress of the questionnaire
        LinearProgressIndicator(
            progress = { progress / 1f },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RectangleShape),
            color = primaryColor,
            trackColor = Color(0xFFD9D9D9)
        )

        Spacer(modifier = Modifier.height(24.dp))

        QuestionDisplay(
            currentQuestionIndex,
            viewModel,
            selectedOptions,
            selectedPersona,
            biggestMealTime,
            wakeUpTime,
            sleepTime
        )

        Spacer(modifier = Modifier.weight(1f))

        // Button to Move on to the next Question
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Back Button
            NutriTrackButton(
                onClick = {
                    // If it is not the First Question, Move on to last Question
                    if (currentQuestionIndex > 0) {
                        currentQuestionIndex -= 1
                    }
                },
                modifier = Modifier.weight(1f),
                // Validation Mechanism that only enable button if it is not empty
                enabled = currentQuestionIndex > 0
            ) {
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            // Next Button
            NutriTrackButton(
                onClick = {
                    // If it is not the Last Question, Move on to next Question
                    if (currentQuestionIndex < 2) {
                        currentQuestionIndex += 1
                    } else {
                        if (viewModel.checkTime()) {
                            viewModel.saveQuestionnaire()

                            val intent = Intent(context, Dashboard::class.java)
                            context.startActivity(intent)
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                // Validation Mechanism that only enable button if it is not empty
                enabled = viewModel.checkButtonEligibility(currentQuestionIndex)
            ) {
                Text(
                    text = if (currentQuestionIndex == 2) "Save" else "Next",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}

/**
 * This is the Composable for Question Display
 * @return a Composable for Question Display
 */
@Composable
private fun QuestionDisplay(
    currentQuestionIndex: Int,
    viewModel: QuestionnaireViewModel,
    selectedOptions: List<String>,
    selectedPersona: String?,
    biggestMealTime: MutableState<String>,
    wakeUpTime: MutableState<String>,
    sleepTime: MutableState<String>
) {
    // Reference: https://stackoverflow.com/questions/67376429/how-to-achieve-slide-in-and-out-animation-in-android-compose-between-two-views-s
    // Reference: Week 10 Lab
    AnimatedContent(
        targetState = currentQuestionIndex,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> -width } + fadeOut()
                )
            } else {
                (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> width } + fadeOut()
                )
            }.using(SizeTransform(clip = false))
        },
        label = "QuestionTransition"
    ) { index ->
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            when (index) {
                0 -> QuestionOne(
                    selectedOptions = selectedOptions,
                    onSelectionChange = { viewModel.onSelectedListChange(it) }
                )

                1 -> QuestionTwo(
                    selectedPersona = selectedPersona,
                    newPersona = { viewModel.onSelectedPersonaChange(it) }
                )

                2 -> QuestionThree(
                    biggestMealTime = biggestMealTime,
                    sleepTime = sleepTime,
                    wakeUpTime = wakeUpTime
                )
            }
        }
    }
}
