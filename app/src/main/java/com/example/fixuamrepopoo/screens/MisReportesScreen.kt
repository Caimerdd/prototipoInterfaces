package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fixuamrepopoo.screens.Reporte

import com.example.fixuamrepopoo.ui.theme.ColorGrisTexto
import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorTexto
@Composable
fun MisReportesScreen(
    reportes: List<Reporte>,
    volver: () -> Unit,
    irDetalleEstado: (Int) -> Unit
) {
    FondoPrincipal {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "< Volver",
                color = ColorPrincipal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { volver() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mis reportes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Text(
                text = "Consulta el estado de tus reportes enviados",
                color = ColorGrisTexto
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (reportes.isEmpty()) {
                TarjetaBlanca(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Aún no has enviado reportes.",
                        color = ColorGrisTexto
                    )
                }
            } else {
                reportes.forEach { reporte ->
                    OpcionMenu(
                        titulo = reporte.tipo,
                        subtitulo = "${reporte.aula} · ${reporte.estado}",
                        letra = reporte.tipo.first().toString(),
                        onClick = {
                            irDetalleEstado(reporte.id)
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}