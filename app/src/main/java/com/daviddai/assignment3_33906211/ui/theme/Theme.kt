package com.daviddai.assignment3_33906211.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    // Kept the Same As Design needed for dark mode
    primary = primaryColor,
    onPrimary = secondaryColor,

    secondary = secondaryVariant,
    onSecondary = onSecondaryColor,

    background = backgroundColor,
    onBackground = onBackgroundColor,

    surface = surfaceColor,
    onSurface = onSurfaceColor,
    surfaceVariant = lightGray,

    outline = borderColor,

    error = errorColor,
    onError = secondaryColor,

    tertiary = scoreColor,
    onTertiary = secondaryColor,
)

val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = secondaryColor,

    secondary = secondaryVariant,
    onSecondary = onSecondaryColor,

    background = backgroundColor,
    onBackground = onBackgroundColor,

    surface = surfaceColor,
    onSurface = onSurfaceColor,
    surfaceVariant = lightGray,

    outline = borderColor,

    error = errorColor,
    onError = secondaryColor,

    tertiary = scoreColor,
    onTertiary = secondaryColor,
)

@Composable
fun NutriTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}