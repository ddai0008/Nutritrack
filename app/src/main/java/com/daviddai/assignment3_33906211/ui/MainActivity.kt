package com.daviddai.assignment3_33906211.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.daviddai.assignment3_33906211.ui.features.login.NutriTrackApp

/**
 * Main Activity for the NutriTrack App.
 * This Act as the Driver of the App.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { NutriTrackApp() }
    }
}



