package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorTexto
import com.example.fixuamrepopoo.ui.theme.ColorGrisTexto

data class EstadisticasDashboard(
    val totalReportes: Int,
    val pendientes: Int,
    val enProceso: Int,
    val resueltos: Int
)

@Composable
fun DashboardScreen(
    rol: String,
    reportes: List<Reporte>,
    usuarioNombre: String,
    onCerrarSesion: () -> Unit,
    onNavigateToInicio: () -> Unit
) {
    // Calcular estadísticas según el rol
    val estadisticas = if (rol == "docente") {
        val misReportes = reportes.filter { it.docente == usuarioNombre }
        EstadisticasDashboard(
            totalReportes = misReportes.size,
            pendientes = misReportes.count { it.estado == "Pendiente" },
            enProceso = misReportes.count { it.estado == "En proceso" },
            resueltos = misReportes.count { it.estado == "Resuelto" }
        )
    } else {
        EstadisticasDashboard(
            totalReportes = reportes.size,
            pendientes = reportes.count { it.estado == "Pendiente" },
            enProceso = reportes.count { it.estado == "En proceso" },
            resueltos = reportes.count { it.estado == "Resuelto" }
        )
    }

    FondoPrincipal {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Dashboard",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorPrincipal
                )

                Text(
                    text = "Hola, $usuarioNombre",
                    fontSize = 18.sp,
                    color = ColorTexto
                )

                val rolTexto = when (rol) {
                    "docente" -> "Docente"
                    "colaborador" -> "Colaborador Técnico"
                    "admin" -> "Administrador"
                    else -> rol
                }

                Text(
                    text = "Rol: $rolTexto",
                    fontSize = 14.sp,
                    color = ColorGrisTexto
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Resumen general",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(16.dp))

                TarjetaMetrica(
                    titulo = "Total reportes",
                    valor = "${estadisticas.totalReportes}",
                    color = ColorPrincipal
                )

                Spacer(modifier = Modifier.height(12.dp))

                TarjetaMetrica(
                    titulo = "Pendientes",
                    valor = "${estadisticas.pendientes}",
                    color = Color(0xFFE95656)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TarjetaMetrica(
                    titulo = "En proceso",
                    valor = "${estadisticas.enProceso}",
                    color = Color(0xFFFFA726)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TarjetaMetrica(
                    titulo = "Resueltos",
                    valor = "${estadisticas.resueltos}",
                    color = Color(0xFF4CAF50)
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = " Reportes recientes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(16.dp))

                val reportesRecientes = if (rol == "docente") {
                    reportes.filter { it.docente == usuarioNombre }.reversed()
                } else {
                    reportes.reversed()
                }

                if (reportesRecientes.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "📭",
                                fontSize = 48.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No hay reportes aún",
                                fontSize = 16.sp,
                                color = ColorGrisTexto
                            )
                            Text(
                                text = "Cuando crees reportes, aparecerán aquí",
                                fontSize = 14.sp,
                                color = ColorGrisTexto
                            )
                        }
                    }
                } else {
                    reportesRecientes.take(10).forEach { reporte ->
                        TarjetaReporteResumen(reporte = reporte)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (estadisticas.totalReportes > 0) {
                    Text(
                        text = "📊 Estadísticas adicionales",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTexto
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val tasaResolucion = (estadisticas.resueltos.toDouble() / estadisticas.totalReportes) * 100
                    TarjetaMetrica(
                        titulo = "Tasa de resolución",
                        valor = String.format("%.1f", tasaResolucion) + "%",
                        color = Color(0xFF4CAF50)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val reportesActivos = estadisticas.pendientes + estadisticas.enProceso
                    TarjetaMetrica(
                        titulo = "Reportes activos",
                        valor = "$reportesActivos",
                        color = Color(0xFFFFA726)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                BotonPrincipal(
                    texto = "Volver al inicio",
                    onClick = onNavigateToInicio
                )

                Spacer(modifier = Modifier.height(12.dp))

                BotonOscuro(
                    texto = "Cerrar sesión",
                    onClick = onCerrarSesion
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun TarjetaMetrica(
    titulo: String,
    valor: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = titulo,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = valor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun TarjetaReporteResumen(reporte: Reporte) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reporte.tipo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = ColorTexto
                )
                Text(
                    text = "Aula: ${reporte.aula}",
                    fontSize = 13.sp,
                    color = ColorGrisTexto
                )
                Text(
                    text = "Fecha: ${reporte.fecha}",
                    fontSize = 12.sp,
                    color = ColorGrisTexto
                )
                if (rolMostrarDocente(reporte)) {
                    Text(
                        text = "Docente: ${reporte.docente}",
                        fontSize = 12.sp,
                        color = ColorGrisTexto
                    )
                }
            }

            val colorEstado = when (reporte.estado) {
                "Pendiente" -> Color(0xFFE95656)
                "En proceso" -> Color(0xFFFFA726)
                "Resuelto" -> Color(0xFF4CAF50)
                else -> Color.Gray
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = colorEstado.copy(alpha = 0.15f))
            ) {
                Text(
                    text = reporte.estado,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorEstado
                )
            }
        }
    }
}

private fun rolMostrarDocente(reporte: Reporte): Boolean {
    return reporte.docente.isNotBlank() && reporte.docente != "Soporte"
}