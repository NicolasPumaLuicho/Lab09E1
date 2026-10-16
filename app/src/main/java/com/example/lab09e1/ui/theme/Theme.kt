package com.example.lab09e1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NaranjaPrincipal,
    onPrimary = Color.White,
    primaryContainer = AmbarClaro,
    onPrimaryContainer = NaranjaOscuro,
    secondary = Ambar,
    onSecondary = TextoOscuro,
    background = FondoCrema,
    onBackground = TextoOscuro,
    surface = Color.White,
    onSurface = TextoOscuro
)

@Composable
fun Lab09E1Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
