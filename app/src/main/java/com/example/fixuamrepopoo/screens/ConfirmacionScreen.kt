package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorTexto

@Composable
fun ConfirmacionScreen(
    irInicio: () -> Unit,
    irMisReportes: () -> Unit
) {
    FondoPrincipal {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(ColorPrincipal, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "¡Reporte enviado!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Tu reporte ha sido recibido correctamente y será atendido según el orden de llegada.",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            TarjetaBlanca(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Número de reporte",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "#RPT-2024-00045",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = ColorPrincipal,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            BotonPrincipal(
                texto = "Ver mis reportes",
                onClick = irMisReportes
            )

            Spacer(modifier = Modifier.height(12.dp))

            BotonPrincipal(
                texto = "Volver al inicio",
                onClick = irInicio
            )
        }
    }
}

