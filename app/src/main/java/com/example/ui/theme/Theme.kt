package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val BoonLightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldLight,
    onPrimaryContainer = EmeraldDark,
    secondary = AmberSecondary,
    onSecondary = Color.White,
    secondaryContainer = AmberLight,
    onSecondaryContainer = Color(0xFF78350F),
    tertiary = SkyBlueAccent,
    onTertiary = Color.White,
    tertiaryContainer = SkyBlueLight,
    onTertiaryContainer = Color(0xFF0369A1),
    background = MintBackground,
    onBackground = TextPrimaryDark,
    surface = Color.White,
    onSurface = TextPrimaryDark,
    surfaceVariant = EmeraldSoft,
    onSurfaceVariant = TextSecondaryDark,
    outline = Color(0xFFCBD5E1)
)

private val BoonDarkColorScheme = darkColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.Black,
    primaryContainer = EmeraldDark,
    onPrimaryContainer = EmeraldLight,
    secondary = AmberGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF78350F),
    onSecondaryContainer = AmberLight,
    tertiary = SkyBlueAccent,
    onTertiary = Color.White,
    background = DarkSurface,
    onBackground = Color(0xFFF1F5F9),
    surface = DarkCard,
    onSurface = TextPrimaryDark, // Black text on white/light inputs
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

val BoonShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun BoonTheme(
    darkTheme: Boolean = false, // Keep cheerful, crisp nature theme with black text
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) BoonDarkColorScheme else BoonLightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = EmeraldPrimary.toArgb()
                window.navigationBarColor = if (darkTheme) DarkSurface.toArgb() else Color.White.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = BoonShapes,
        typography = Typography,
        content = content
    )
}
