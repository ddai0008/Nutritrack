package com.daviddai.assignment3_33906211.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/*
 CHATGPT was used to generate the extended color scheme based on the colour scheme in Assignment 1
 */

// Brand Colors
val primaryColor = Color(0xFFFE8A22)       // Vibrant Orange
val gradientPrimary = Color(0xFFFF9D00)    // Used for Gradient
val secondaryColor = Color(0xFFFFFFFF)     // White
val secondaryVariant = Color(0xFFF5F5F5)   // Subtle gray-white

// Backgrounds & Surfaces
val backgroundColor = Color(0xFFFDFDFD)    // App background
val surfaceColor = Color(0xFFFFFFFF)       // Card/Dialog surface
val surfaceVariant = Color(0xFFEDEDED)     // Slight gray tone surface

// Text Colors
val onPrimaryColor = Color(0xFFFFFFFF)     // Text on primary (white)
val onSecondaryColor = Color(0xFF1A1A1A)   // Text on secondary
val onBackgroundColor = Color(0xFF1A1A1A)  // Primary text on background
val onSurfaceColor = Color(0xFF333333)     // Text on surface

// Neutral & Grays
val lightGray = Color(0xFFEEEEEE)          // Light UI elements
val mediumGray = Color(0xFFB0B0B0)         // Placeholders, hints
val darkGray = Color(0xFF4F4F4F)           // Subheadings

// Feedback Colors
val scoreColor = Color(0xFF00A300)         // Success / Positive
val errorColor = Color(0xFFD32F2F)         // Red
val warningColor = Color(0xFFFFA000)       // Orange Yellow
val infoColor = Color(0xFF1976D2)          // Blue

// Progress & Borders
val progressTrack = Color(0xFFD9D9D9)      // Background of progress bars
val progressIndicator = primaryColor       // Foreground progress
val borderColor = Color(0xFFE0E0E0)         // Input borders


// Reference: https://developer.android.com/develop/ui/compose/graphics/draw/brush
val orangeGradient = Brush.linearGradient(
    colors = listOf(
        primaryColor,
        gradientPrimary
    )
)

// Button Disabled color
val disabledGradient = Brush.horizontalGradient(
    colors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.6f)
    )
)

// Image Gradient cover
// Reference: https://stackoverflow.com/questions/63871706/gradient-over-image-in-jetpack-compose
val imageGradient = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        primaryColor.copy(alpha = 0.6f)
    )
)


