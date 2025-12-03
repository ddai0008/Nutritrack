package com.daviddai.assignment3_33906211.ui.features.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daviddai.assignment3_33906211.data.viewModel.GenAiViewModel
import com.daviddai.assignment3_33906211.data.viewModel.LoginViewModel
import com.daviddai.assignment3_33906211.data.viewModel.ScoreViewModel
import com.daviddai.assignment3_33906211.data.viewModel.SettingViewModel
import com.daviddai.assignment3_33906211.ui.features.insightAnalysis.InsightScreen
import com.daviddai.assignment3_33906211.ui.features.nutricoach.NutriCoach
import com.daviddai.assignment3_33906211.ui.features.setting.ClinicianScreen
import com.daviddai.assignment3_33906211.ui.features.setting.SettingScreen

/**
 * This is the Composable for the Navigation Host
 * @param innerPadding is the inner padding
 * @param navController is the navigation controller
 */
@Composable
fun MyNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {
    // View Models for each screen
    val scoreViewModel: ScoreViewModel = viewModel()
    val settingViewModel: SettingViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val genAiViewModel: GenAiViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") {
            CenteredColumn { HomeScreen(innerPadding, scoreViewModel, navController) }
        }

        composable("Insights") {
            CenteredColumn { InsightScreen(innerPadding, scoreViewModel, navController) }
        }

        composable("NutriCoach") {
            CenteredColumn { NutriCoach(innerPadding, genAiViewModel) }
        }

        composable("Settings") {
            CenteredColumn { SettingScreen(innerPadding, settingViewModel, loginViewModel, navController) }
        }

        composable("ClinicianScreen") {
            CenteredColumn { ClinicianScreen(innerPadding, genAiViewModel, scoreViewModel) }
        }
    }
}

// Used to Center Element when rotated
@Composable
private fun CenteredColumn(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}