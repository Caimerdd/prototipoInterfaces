package com.example.fixuamrepopoo.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.*

import com.example.fixuamrepopoo.screens.*
import com.example.fixuamrepopoo.ui.theme.ConfiguracionTema
import com.example.fixuamrepopoo.ui.theme.LocalConfiguracionTema
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {

    var pantallaActual by remember { mutableStateOf("login") }
    var rolSeleccionado by remember { mutableStateOf("") }

    var modoOscuro by remember { mutableStateOf(false) }

    val reportes = remember {
        mutableStateListOf<Reporte>()
    }

    var reporteTemporal by remember {
        mutableStateOf<Reporte?>(null)
    }

    var reporteSeleccionadoId by remember {
        mutableStateOf<Int?>(null)
    }

    CompositionLocalProvider(
        LocalConfiguracionTema provides ConfiguracionTema(
            modoOscuro = modoOscuro,
            cambiarModo = {
                modoOscuro = !modoOscuro
            }
        )
    ) {
        AnimatedContent(
            targetState = pantallaActual,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(350),
                    initialOffsetX = { fullWidth -> fullWidth }
                ) + fadeIn(
                    animationSpec = tween(350)
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(350),
                    targetOffsetX = { fullWidth -> -fullWidth }
                ) + fadeOut(
                    animationSpec = tween(350)
                )
            },
            label = "TransicionPantallas"
        ) { pantalla ->

            when (pantalla) {

                "login" -> LoginScreen(
                    seleccionarRol = { rol ->
                        rolSeleccionado = rol
                        pantallaActual = "credenciales"
                    }
                )

                "credenciales" -> CredencialesScreen(
                    rol = rolSeleccionado,
                    volver = {
                        pantallaActual = "login"
                    },
                    ingresar = {
                        pantallaActual = when (rolSeleccionado) {
                            "docente" -> "inicio_docente"
                            "colaborador" -> "colaborador"
                            "admin" -> "colaborador"
                            else -> "login"
                        }
                    }
                )

                "inicio_docente" -> InicioDocenteScreen(
                    irNuevoReporte = {
                        pantallaActual = "nuevo_reporte"
                    },
                    irMisReportes = {
                        pantallaActual = "mis_reportes"
                    },
                    irPerfil = {
                        pantallaActual = "perfil_docente"
                    }
                )

                "nuevo_reporte" -> NuevoReporteScreen(
                    volver = {
                        pantallaActual = "inicio_docente"
                    },
                    continuar = { reporte ->
                        reporteTemporal = reporte
                        pantallaActual = "detalle_reporte"
                    }
                )

                "detalle_reporte" -> DetalleReporteScreen(
                    reporte = reporteTemporal,
                    volver = {
                        pantallaActual = "nuevo_reporte"
                    },
                    enviarReporte = {
                        reporteTemporal?.let { reporte ->
                            reportes.add(reporte)
                        }

                        reporteTemporal = null
                        pantallaActual = "confirmacion"
                    }
                )

                "confirmacion" -> ConfirmacionScreen(
                    irInicio = {
                        pantallaActual = "inicio_docente"
                    },
                    irMisReportes = {
                        pantallaActual = "mis_reportes"
                    }
                )

                "mis_reportes" -> MisReportesScreen(
                    reportes = reportes,
                    volver = {
                        pantallaActual = "inicio_docente"
                    },
                    irDetalleEstado = { id ->
                        reporteSeleccionadoId = id
                        pantallaActual = "detalle_estado"
                    }
                )

                "detalle_estado" -> DetalleEstadoScreen(
                    reporte = reportes.find { it.id == reporteSeleccionadoId },
                    volver = {
                        pantallaActual = "mis_reportes"
                    },
                    cancelarReporte = { id ->
                        val reporte = reportes.find { it.id == id }

                        if (reporte != null) {
                            reportes.remove(reporte)
                        }

                        pantallaActual = "mis_reportes"
                    }
                )

                "perfil_docente" -> PerfilDocenteScreen(
                    volver = {
                        pantallaActual = "inicio_docente"
                    },
                    cerrarSesion = {
                        pantallaActual = "login"
                    }
                )

                "colaborador" -> ColaboradorScreen(
                    reportes = reportes,
                    tomarReporte = { id ->
                        val index = reportes.indexOfFirst { it.id == id }

                        if (index != -1) {
                            val reporteActual = reportes[index]

                            reportes[index] = reporteActual.copy(
                                estado = "En proceso",
                                atendidoPor = "Jaajajja"
                            )
                        }
                    },
                    resolverReporte = { id ->
                        val index = reportes.indexOfFirst { it.id == id }

                        if (index != -1) {
                            val reporteActual = reportes[index]

                            reportes[index] = reporteActual.copy(
                                estado = "Resuelto"
                            )
                        }
                    },
                    cerrarSesion = {
                        pantallaActual = "login"
                    }
                )
            }
        }
    }
}