package com.daviddai.assignment3_33906211.ui.features.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.data.user.User
import com.daviddai.assignment3_33906211.data.viewModel.ScoreViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.features.questionnaires.QuestionnaireScreen
import com.daviddai.assignment3_33906211.ui.theme.NutriTrackTheme
import com.daviddai.assignment3_33906211.ui.theme.backgroundColor
import com.daviddai.assignment3_33906211.ui.theme.lightGray

// Dashboard Activity
class Dashboard : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriTrackTheme {
                // define the navController for navigation
                val navController: NavHostController = rememberNavController()

                Scaffold(
                    // Visual Adjustment
                    modifier = Modifier.fillMaxSize(),
                    containerColor = backgroundColor,

                    // Bottom Navigation Bar
                    bottomBar = { MyBottomBar(navController) }
                ) { innerPadding ->

                    // Navigation Host
                    MyNavHost(innerPadding, navController)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: ScoreViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getExistingUser()
    }

    Column(
        modifier = Modifier
            .widthIn(max = 600.dp)
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    )
    {
        DisplayUserID(viewModel.existingUser?.name.toString()) // Display Hello, UserID

        EditQuestionnaire(context) // Rows for Edit Questionnaire

        // Dynamic Food Plate
        Image(
            painter = painterResource(id = R.drawable.foodplate),
            contentDescription = "Food Plate",
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.FillHeight
        )

        ShowAllScore(navController) // Rows for See all Score

        ShowFoodQualityScore(viewModel.existingUser, viewModel)

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider(thickness = 1.dp, color = lightGray)

        Text(
            text = "What is the Food Quality Score?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.food_score_description),
            fontSize = 12.sp,
            lineHeight = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

/**
 * This is the Composable for the User Name
 */
@Composable
private fun DisplayUserID(userName: String) {
    Text(
        text = "Hello,",
        style = MaterialTheme.typography.titleMedium,
        color = Color.Gray
    )

    Text(
        text = userName,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

/**
 * This is the Composable for the Edit Questionnaire
 * @param context is the context
 */
@Composable
private fun EditQuestionnaire(
    context: Context
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =
                "You've already filled in your Food Intake " +
                        "Questionnaire, but you can change details here",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
        )

        NutriTrackButton(
            onClick = { context.startActivity(Intent(context, QuestionnaireScreen::class.java)) },
            buttonShape = shapes.small,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            buttonModifier = Modifier
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Edit Question Answer",
                Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = "Edit",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

/**
 * This is the Composable for the See All Score
 * @param navController is the navigation controller
 */
@Composable
private fun ShowAllScore(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "My Score",
            style = MaterialTheme.typography.titleLarge,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate("Insights")
                }
        ) {
            Text(
                text = "See All Score",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )

            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "See all Scores",
                tint = Color.Gray
            )
        }
    }
}

/**
 * This is the Composable for the Food Quality Score
 * @param user is the user data
 */
@Composable
private fun ShowFoodQualityScore(
    user: User?,
    viewModel: ScoreViewModel
) {
    val totalFoodQuality = user?.totalScore?.toFloat() ?: 0f


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = lightGray, shape = RoundedCornerShape(24.dp))
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.uparrow),
                contentDescription = "Food Quality Score Indicator",
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = "Your Food Quality Score",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${totalFoodQuality}/100",
            fontSize = 16.sp,
            color = viewModel.scoreColor(totalFoodQuality),
            fontWeight = FontWeight.Bold,
        )
    }
}





