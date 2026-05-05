package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fixuamrepopoo.screens.Reporte
import com.example.fixuamrepopoo.screens.BotonOscuro
import com.example.fixuamrepopoo.ui.theme.ColorFondo
import com.example.fixuamrepopoo.ui.theme.ColorGrisTexto
import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorTexto
import com.example.fixuamrepopoo.screens.FondoPrincipal
import com.example.fixuamrepopoo.screens.TarjetaBlanca

import kotlin.collections.filter

@Composable
fun ColaboradorScreen(
    reportes: List<Reporte>,
    tomarReporte: (Int) -> Unit,
    resolverReporte: (Int) -> Unit,
    cerrarSesion: () -> Unit
) {
    var pestanaActual by remember { mutableStateOf("pendientes") }

    val reportesPendientes = reportes.filter { it.estado == "Pendiente" }
    val misTareas = reportes.filter { it.estado == "En proceso" }

    FondoPrincipal {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Panel colaborador",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Text(
                text = "Gestiona los reportes tecnológicos recibidos",
                fontSize = 17.sp,
                color = ColorGrisTexto
            )

            Spacer(modifier = Modifier.height(22.dp))

            TarjetaBlanca(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Soporte técnico",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Colaborador: Jaajajja",
                    fontSize = 17.sp,
                    color = ColorGrisTexto
                )

                Text(
                    text = "Área: Dirección Tecnológica",
                    fontSize = 17.sp,
                    color = ColorGrisTexto
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonPestanaColaborador(
                    texto = "Pendientes",
                    seleccionado = pestanaActual == "pendientes",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        pestanaActual = "pendientes"
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                BotonPestanaColaborador(
                    texto = "Mis tareas",
                    seleccionado = pestanaActual == "mis_tareas",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        pestanaActual = "mis_tareas"
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                if (pestanaActual == "pendientes") {
                    if (reportesPendientes.isEmpty()) {
                        MensajeColaboradorVacio(
                            titulo = "No hay reportes pendientes",
                            descripcion = "Cuando un docente envíe un reporte, aparecerá aquí para que pueda ser tomado por soporte."
                        )
                    } else {
                        reportesPendientes.forEach { reporte ->
                            TarjetaReporteColaborador(
                                reporte = reporte,
                                textoBoton = "Tomar reporte",
                                onClickBoton = {
                                    tomarReporte(reporte.id)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                } else {
                    if (misTareas.isEmpty()) {
                        MensajeColaboradorVacio(
                            titulo = "No tenés tareas asignadas",
                            descripcion = "Los reportes que tomés desde pendientes aparecerán en esta sección."
                        )
                    } else {
                        misTareas.forEach { reporte ->
                            TarjetaReporteColaborador(
                                reporte = reporte,
                                textoBoton = "Marcar como solucionado",
                                onClickBoton = {
                                    resolverReporte(reporte.id)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BotonOscuro(
                texto = "Cerrar sesión",
                onClick = cerrarSesion
            )

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun BotonPestanaColaborador(
    texto: String,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(54.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (seleccionado) ColorPrincipal else Color(0xFFE0E0E0)
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionado) {
                ColorPrincipal
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = texto,
                fontWeight = FontWeight.Bold,
                color = if (seleccionado) Color.White else ColorTexto
            )
        }
    }
}

@Composable
fun TarjetaReporteColaborador(
    reporte: Reporte,
    textoBoton: String,
    onClickBoton: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RPT-${reporte.id}",
                    modifier = Modifier.weight(1f),
                    color = ColorPrincipal,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                EstadoColaboradorChip(
                    estado = reporte.estado
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = reporte.tipo,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Aula: ${reporte.aula}",
                fontSize = 17.sp,
                color = ColorGrisTexto
            )

            Text(
                text = "Docente: ${reporte.docente}",
                fontSize = 17.sp,
                color = ColorGrisTexto
            )

            Text(
                text = "Fecha: ${reporte.fecha}",
                fontSize = 16.sp,
                color = ColorGrisTexto
            )

            if (reporte.atendidoPor.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Atendido por: ${reporte.atendidoPor}",
                    fontSize = 16.sp,
                    color = ColorPrincipal,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Descripción",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Text(
                text = reporte.descripcion,
                fontSize = 16.sp,
                color = ColorGrisTexto
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onClickBoton,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPrincipal,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = textoBoton,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EstadoColaboradorChip(
    estado: String
) {
    val colorEstado = when (estado) {
        "Pendiente" -> Color(0xFFE95656)
        "En proceso" -> Color(0xFFFFA726)
        "Resuelto" -> ColorPrincipal
        else -> ColorGrisTexto
    }

    Card(
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorEstado.copy(alpha = 0.12f)
        )
    ) {
        Text(
            text = estado.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = colorEstado,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MensajeColaboradorVacio(
    titulo: String,
    descripcion: String
) {
    TarjetaBlanca(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = titulo,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTexto
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = descripcion,
            fontSize = 16.sp,
            color = ColorGrisTexto
        )
    }
}