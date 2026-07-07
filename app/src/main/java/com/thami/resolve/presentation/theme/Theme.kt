package com.thami.resolve.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ThamiResolveColorScheme = darkColorScheme(
    primary = ElectricViolet,
    secondary = AquaGlow,
    background = MidnightBlue,
    surface = DeepIndigo,
    onPrimary = SoftWhite,
    onSecondary = MidnightBlue,
    onBackground = SoftWhite,
    onSurface = SoftWhite,
    error = DangerRed
)

@Composable
fun ThamiResolveTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ThamiResolveColorScheme,
        typography = ThamiResolveTypography,
        content = content
    )
}
