package com.example.spaceconvert.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VeryLightBlue,
    onPrimary = DarkBlue,
    secondary = Black,
    onSecondary = VeryLightBlue,
    tertiary = LightBlue,
    onTertiary = DarkBlue,
    background = DarkBlue,
    onBackground = VeryLightBlue,
    surface = DarkBlue,
    onSurface = VeryLightBlue
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    onPrimary = LightBlue,
    secondary = VeryLightBlue,
    onSecondary = DarkBlue,
    tertiary = VeryLightBlue,
    onTertiary = DarkBlue,
    background = LightBlue,
    onBackground = DarkBlue,
    surface = LightBlue,
    onSurface = DarkBlue
)

@Composable
fun SpaceConvertTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}