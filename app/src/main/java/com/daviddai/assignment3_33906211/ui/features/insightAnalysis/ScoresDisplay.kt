package com.daviddai.assignment3_33906211.ui.features.insightAnalysis


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daviddai.assignment3_33906211.data.viewModel.ScoreViewModel
import kotlin.math.round

/**
 * This is the Composable for the Score Display
 * @param scoreList is the list of scores
 * @param viewModel is the view model for the score
 */
@Composable
fun ScoreDisplay(
    scoreList: List<HeifaScore>,
    viewModel: ScoreViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .heightIn(max = 500.dp) // Maximum height is required for it's super to scroll
    ) {
        items(scoreList) { heifaScore ->
            // Score Variable
            val score = heifaScore.score.toFloat()
            val maximum = heifaScore.maximum.toFloat()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = heifaScore.category,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { score / maximum },
                        color = viewModel.scoreColor(score, maximum),
                        gapSize = 0.dp,
                        drawStopIndicator = {},
                        modifier = Modifier
                            .padding(10.dp)
                            .width(140.dp)
                            .height(6.dp)
                    )

                    Text(
                        text = "${score}/${maximum.toInt()}",
                        Modifier.width(60.dp)
                    )
                }
            }
        }
    }
}