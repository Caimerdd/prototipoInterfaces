package com.example.proyecto_fixuam.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_fixuam.ui.theme.ColorGrisTexto
import com.example.proyecto_fixuam.ui.theme.ColorPrincipal
import com.example.proyecto_fixuam.ui.theme.ColorTexto

@Composable
fun InicioDocenteScreen(
    irNuevoReporte: () -> Unit,
    irMisReportes: () -> Unit,
    irPerfil: () -> Unit
) {
    FondoPrincipal {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "¡Hola, Docente!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Text(
                text = "¿Qué deseas hacer hoy?",
                color = ColorGrisTexto
            )

            Spacer(modifier = Modifier.height(26.dp))

            OpcionMenu(
                titulo = "Nuevo reporte",
                subtitulo = "Reporta un problema en tu aula",
                letra = "R",
                onClick = irNuevoReporte
            )

            Spacer(modifier = Modifier.height(16.dp))

            OpcionMenu(
                titulo = "Mis reportes",
                subtitulo = "Consulta el estado de tus reportes",
                letra = "M",
                onClick = irMisReportes
            )

            Spacer(modifier = Modifier.height(16.dp))

            OpcionMenu(
                titulo = "Mi perfil",
                subtitulo = "Información del docente",
                letra = "P",
                onClick = irPerfil
            )

        }
    }
}