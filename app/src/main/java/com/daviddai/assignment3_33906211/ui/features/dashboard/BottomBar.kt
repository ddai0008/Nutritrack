package com.daviddai.assignment3_33906211.ui.features.dashboard


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.daviddai.assignment3_33906211.R
import com.daviddai.assignment3_33906211.ui.theme.lightGray
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import com.daviddai.assignment3_33906211.ui.theme.primaryColor
import com.daviddai.assignment3_33906211.ui.theme.secondaryColor

/**
 * This is the Composable for the Bottom Navigation Bar
 * @param navController is the navigation controller
 * @return a Bottom Navigation Bar
 */
@Composable
fun MyBottomBar(navController: NavHostController) {

    // Reference: https://stackoverflow.com/questions/66493551/jetpack-compose-navigation-get-route-of-current-destination-as-string
    // Get current route to highlight the icon
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Nav Bar Items
    var items = listOf("Home", "Insights", "NutriCoach", "Settings")

    NavigationBar(
        containerColor = secondaryColor,
        modifier = Modifier.border(1.dp, color = lightGray)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                // Color Adjustment
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = primaryColor, // Highlighted Box Color
                    selectedIconColor = onPrimaryColor, // Highlighted Icon Color
                    unselectedIconColor = primaryColor // Default Color
                ),

                icon = {
                    when (item) {
                        // Icon for Home
                        "Home" -> Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )

                        // Icon for Insights
                        "Insights" -> Icon(
                            painterResource(R.drawable.insights),
                            contentDescription = "Insights",
                            modifier = Modifier.size(24.dp)
                        )

                        // Icon for NutriCoach
                        "NutriCoach" -> Icon(
                            painterResource(R.drawable.intelligence),
                            contentDescription = "NutriCoach",
                            modifier = Modifier.size(24.dp)
                        )

                        // Icon for Settings
                        "Settings" -> Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                // Nav Bar Location Name
                label = { Text(item) },
                selected = currentRoute == item,
                onClick = {
                    navController.navigate(item) // Navigate to the route upon Clicking
                }
            )
        }
    }
}