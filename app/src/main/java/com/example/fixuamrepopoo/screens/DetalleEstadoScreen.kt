package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fixuamrepopoo.screens.Reporte
import com.example.fixuamrepopoo.screens.BotonOscuro
import com.example.fixuamrepopoo.screens.FondoPrincipal
import com.example.fixuamrepopoo.screens.TarjetaBlanca

import com.example.fixuamrepopoo.ui.theme.ColorGrisTexto
import com.example.fixuamrepopoo.ui.theme.ColorNaranja
import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorTexto

@Composable
fun DetalleEstadoScreen(
    reporte: Reporte?,
    volver: () -> Unit,
    cancelarReporte: (Int) -> Unit
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
                modifier = Modifier.clickable {
                    volver()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Estado del reporte",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (reporte == null) {
                TarjetaBlanca(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No se encontró el reporte.",
                        color = ColorGrisTexto
                    )
                }
            } else {
                TarjetaBlanca(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = reporte.tipo,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTexto
                    )

                    Text(
                        text = "Aula: ${reporte.aula}",
                        color = ColorGrisTexto
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Estado: ${reporte.estado}",
                        color = if (reporte.estado == "Resuelto") ColorPrincipal else ColorNaranja,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Descripción",
                        fontWeight = FontWeight.Bold,
                        color = ColorTexto
                    )

                    Text(
                        text = reporte.descripcion,
                        color = ColorGrisTexto
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Prioridad",
                        fontWeight = FontWeight.Bold,
                        color = ColorTexto
                    )

                    Text(
                        text = reporte.prioridad,
                        color = ColorGrisTexto
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Fecha",
                        fontWeight = FontWeight.Bold,
                        color = ColorTexto
                    )

                    Text(
                        text = reporte.fecha,
                        color = ColorGrisTexto
                    )

                    if (reporte.atendidoPor.isNotBlank()) {
                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Atendido por",
                            fontWeight = FontWeight.Bold,
                            color = ColorTexto
                        )

                        Text(
                            text = reporte.atendidoPor,
                            color = ColorPrincipal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                if (reporte.estado != "Resuelto") {
                    BotonOscuro(
                        texto = "Cancelar reporte",
                        onClick = {
                            cancelarReporte(reporte.id)
                        }
                    )
                } else {
                    TarjetaBlanca(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Este reporte ya fue solucionado por soporte técnico.",
                            color = ColorPrincipal,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}