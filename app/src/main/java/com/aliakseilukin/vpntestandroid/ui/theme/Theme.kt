package com.aliakseilukin.vpntestandroid.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnLight,
    primaryContainer = TertiaryLight,
    onPrimaryContainer = Color.White,

    secondary = SecondaryLight,
    onSecondary = Color.White,

    tertiary = TertiaryLight,
    onTertiary = Color.White,

    background = NeutralLight,
    onBackground = OnNeutralLight,

    surface = NeutralLight,
    onSurface = OnNeutralLight
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnDark,
    primaryContainer = TertiaryDark,
    onPrimaryContainer = Color.White,

    secondary = SecondaryDark,
    onSecondary = Color.White,

    tertiary = TertiaryDark,
    onTertiary = Color.White,

    background = NeutralDark,
    onBackground = OnNeutralDark,

    surface = NeutralDark,
    onSurface = OnNeutralDark
)

@Composable
fun VPNTestAndroidTheme(
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
        content = content
    )
}