package com.example.uamfix.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Traemos los roles del modelo
import com.example.uamfix.model.RolUsuario
// Traemos todas las pantallas
import com.example.uamfix.pantallas.*

@Composable
fun NavegacionApp() {
    val controladorNavegacion = rememberNavController()

    NavHost(
        navController = controladorNavegacion,
        startDestination = "login",
        enterTransition = {
            slideInHorizontally(animationSpec = tween(500), initialOffsetX = { fullWidth -> fullWidth }) + fadeIn(tween(500))
        },
        exitTransition = {
            slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut(tween(500))
        }
    ) {
        composable("login") {
            PantallaLogin(
                alIniciarSesion = { rol ->
                    when (rol) {
                        RolUsuario.ADMINISTRADOR -> controladorNavegacion.navigate("admin")
                        RolUsuario.DOCENTE -> controladorNavegacion.navigate("docente")
                        RolUsuario.COLABORADOR -> controladorNavegacion.navigate("colaborador")
                    }
                }
            )
        }

        composable("admin") { PantallaAdministrador() }

        composable("docente") {
            PantallaDocente(
                alCerrarSesion = {
                    // Al cerrar sesión, regresamos al login y limpiamos el historial
                    controladorNavegacion.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("colaborador") {
            PantallaColaborador(
                alCerrarSesion = {
                    // Al cerrar sesión, regresamos al login y limpiamos el historial
                    controladorNavegacion.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}