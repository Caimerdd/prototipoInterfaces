package com.example.fixuamrepopoo.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class ConfiguracionTema(
    val modoOscuro: Boolean,
    val cambiarModo: () -> Unit
)

val LocalConfiguracionTema = compositionLocalOf {
    ConfiguracionTema(
        modoOscuro = false,
        cambiarModo = {}
    )
}

fun fondoApp(modoOscuro: Boolean): Color {
    return if (modoOscuro) Color(0xFF071A1C) else Color(0xFFF4FAFB)
}

fun tarjetaApp(modoOscuro: Boolean): Color {
    return if (modoOscuro) Color(0xFF102A2D) else Color.White
}

fun textoApp(modoOscuro: Boolean): Color {
    return if (modoOscuro) Color.White else Color(0xFF102A43)
}

fun textoSecundarioApp(modoOscuro: Boolean): Color {
    return if (modoOscuro) Color(0xFFB8C7C9) else Color(0xFF7A8A99)
}

fun colorPrincipalApp(): Color {
    return Color(0xFF32A0A6)
}