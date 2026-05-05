package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
fun AdministradorScreen(
    reportes: List<Reporte>,
    usuarios: List<String> = listOf("Juan Pérez", "Luis Guadamuz", "Maria Lopez"), // Ejemplo
    eliminarReporte: (Int) -> Unit,
    cerrarSesion: () -> Unit
) {
    // Añadimos más estados para las pestañas de Admin
    var pestanaActual by remember { mutableStateOf("pendientes") }

    val reportesPendientes = reportes.filter { it.estado == "Pendiente" }
    val reportesResueltos = reportes.filter { it.estado == "Resuelto" }

    FondoPrincipal {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Panel Administrador",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Text(
                text = "Control total del sistema de reportes",
                fontSize = 17.sp,
                color = ColorGrisTexto
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Tarjeta de Info del Admin
            TarjetaBlanca(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Admin: Luis Carlos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorTexto
                        )
                        Text(
                            text = "Rol: Superusuario",
                            fontSize = 16.sp,
                            color = ColorPrincipal,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    // Un pequeño indicador de cuántos pendientes hay
                    EstadoColaboradorChip(estado = "${reportesPendientes.size} Alertas")
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Selector de pestañas con Scroll horizontal por si agregás más
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                BotonPestanaColaborador(
                    texto = "Pendientes",
                    seleccionado = pestanaActual == "pendientes",
                    modifier = Modifier.width(140.dp),
                    onClick = { pestanaActual = "pendientes" }
                )
                Spacer(modifier = Modifier.width(10.dp))
                BotonPestanaColaborador(
                    texto = "Historial",
                    seleccionado = pestanaActual == "historial",
                    modifier = Modifier.width(140.dp),
                    onClick = { pestanaActual = "historial" }
                )
                Spacer(modifier = Modifier.width(10.dp))
                BotonPestanaColaborador(
                    texto = "Personal",
                    seleccionado = pestanaActual == "personal",
                    modifier = Modifier.width(140.dp),
                    onClick = { pestanaActual = "personal" }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                when (pestanaActual) {
                    "pendientes" -> {
                        if (reportesPendientes.isEmpty()) {
                            MensajeColaboradorVacio("Todo al día", "No hay reportes críticos sin atender.")
                        } else {
                            reportesPendientes.forEach { reporte ->
                                TarjetaReporteAdmin(
                                    reporte = reporte,
                                    accionPrimaria = "Eliminar",
                                    colorAccion = Color(0xFFE95656), // Rojo para borrar
                                    onClick = { eliminarReporte(reporte.id) }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                    "historial" -> {
                        if (reportesResueltos.isEmpty()) {
                            MensajeColaboradorVacio("Historial vacío", "Aún no se han completado reportes.")
                        } else {
                            reportesResueltos.forEach { reporte ->
                                TarjetaReporteAdmin(
                                    reporte = reporte,
                                    accionPrimaria = "Ver Detalles",
                                    colorAccion = ColorPrincipal,
                                    onClick = { /* Navegar a detalle */ }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                    "personal" -> {
                        Text("Gestión de Colaboradores", fontWeight = FontWeight.Bold, color = ColorTexto)
                        Spacer(modifier = Modifier.height(10.dp))
                        usuarios.forEach { user ->
                            TarjetaUsuario(nombre = user)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            BotonOscuro(texto = "Cerrar sesión", onClick = cerrarSesion)
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun TarjetaReporteAdmin(
    reporte: Reporte,
    accionPrimaria: String,
    colorAccion: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("ID: ${reporte.id}", modifier = Modifier.weight(1f), color = ColorPrincipal, fontWeight = FontWeight.Bold)
                EstadoColaboradorChip(estado = reporte.estado)
            }
            Text(text = reporte.tipo, fontSize = 19.sp, fontWeight = FontWeight.Bold, color = ColorTexto)
            Text(text = "Docente: ${reporte.docente}", fontSize = 15.sp, color = ColorGrisTexto)

            if (reporte.estado == "Resuelto") {
                Text(text = "Finalizado por: ${reporte.atendidoPor}", fontSize = 14.sp, color = ColorPrincipal)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorAccion)
            ) {
                Text(text = accionPrimaria, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TarjetaUsuario(nombre: String) {
    TarjetaBlanca(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                // Icono simulado
                Text("👤", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = nombre, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = ColorTexto)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Activo", color = Color(0xFF4CAF50), fontSize = 12.sp)
        }
    }
}