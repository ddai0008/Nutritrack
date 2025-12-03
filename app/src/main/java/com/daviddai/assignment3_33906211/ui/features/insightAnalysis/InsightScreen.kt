package com.daviddai.assignment3_33906211.ui.features.insightAnalysis

import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.data.viewModel.ScoreViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriLargeTitle
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.Shapes
import com.daviddai.assignment3_33906211.ui.theme.onSecondaryColor
import com.daviddai.assignment3_33906211.ui.theme.primaryColor

/**
 * This is the Composable for the Insight Screen
 * @param innerPadding is the inner padding
 * @param viewModel is the view model for the score
 * @param navController is the navigation controller
 */
@Composable
fun InsightScreen(
    innerPadding: PaddingValues,
    viewModel: ScoreViewModel,
    navController: NavController
) {
    // Get Current Context
    val context = LocalContext.current

    // ViewModel Element for UI Display
    val userDetail = viewModel.existingUser
    val totalScore = userDetail?.totalScore!!.toFloat()
    val scores = viewModel.scores

    // Handle display when rotation
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .widthIn(max = 600.dp )
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = 18.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        displayTitle("Insights: Food Score")

        ScoreDisplay(scores, viewModel)

        displayTitle(
            title = "Total Food Quality Score",
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.Start)
        )

        TotalScoreDisplay(totalScore, viewModel)

        Spacer(modifier = Modifier.height(32.dp))

        ScoreButton(totalScore, scores, context, navController)
    }
}

/**
 * This is the Composable for the Large title
 * @param title is the String to display
 * @param color is the color of the title
 * @param modifier is the modifier for the title
 */
@Composable
private fun displayTitle(
    title: String,
    color: Color = onSecondaryColor,
    modifier: Modifier = Modifier
) {
    NutriLargeTitle(
        title = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = modifier.padding(vertical = 12.dp)
    )
}

/**
 * This is the Composable for the Total Score Display
 * @param totalScore is the total score of the user
 * @param viewModel is the view model for the score
 */
@Composable
private fun TotalScoreDisplay(
    totalScore: Float,
    viewModel: ScoreViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        LinearProgressIndicator(
            progress = { totalScore / 100f },
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(0.8f)
                .height(6.dp),
            color = primaryColor,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )

        Text(
            text = "${totalScore}/100",
            style = MaterialTheme.typography.bodyLarge,
            color = viewModel.scoreColor(totalScore)
        )
    }
}

/**
 * This is the Composable for the Buttons
 */
@Composable
private fun ScoreButton(
    totalScore: Float,
    scores: List<HeifaScore>,
    context: android.content.Context,
    navController: NavController
) {
    val buttonWidth = 250.dp

    // Improve My Diet
    NutriTrackButton(
        onClick = {
            // Navigate to NutriCoach
            navController.navigate("NutriCoach")
        },
        buttonShape = Shapes.large,
        buttonModifier = Modifier.width(buttonWidth)
    ) {
        Icon(
            painterResource(R.drawable.rocket),
            contentDescription = "Improve my Diet",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "Improve my Diet",
            modifier = Modifier.padding(start = 8.dp)
        )
    }

    // Share with Someone
    NutriTrackButton(
        onClick = {
            // Reference: Lab Week 4
            // Share the HEIFA score
            val shareIntent = Intent(ACTION_SEND)
            shareIntent.type = "text/plain"
            var shareText = "Total Food Quality: ${totalScore}\n"

            scores.forEach { heifaScore -> // Add the other Categories
                shareText += "${heifaScore.category}: ${heifaScore.score}\n"
            }

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            context.startActivity(Intent.createChooser(shareIntent, "Share text via"))
        },
        buttonShape = Shapes.large,
        buttonModifier = Modifier.width(buttonWidth)
    ) {
        Icon(
            Icons.Filled.Share,
            contentDescription = "Share with Someone"
        )
        Text(
            text = "Share with Someone",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}