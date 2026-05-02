package com.example.uamfix.pantallas

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.example.uamfix.model.RolUsuario
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun PantallaLogin(alIniciarSesion: (RolUsuario) -> Unit) {
    val colorPrincipal = Color(0xFF229799)

    // VARIABLES PARA LA ANIMACIÓN DEL FONDO AQUA
    val transicionInfinita = rememberInfiniteTransition(label = "FondoAnimado")

    // Movemos el gradiente en el eje X
    val moverX by transicionInfinita.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse // Va y viene suavemente
        ),
        label = "MoverX"
    )

    // Movemos el gradiente en el eje Y (con un tiempo distinto para que sea asimétrico y más natural)
    val moverY by transicionInfinita.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MoverY"
    )

    // Creamos el pincel (Brush) con el gradiente de colores Aqua y Teal
    val gradienteFondo = Brush.linearGradient(
        colors = listOf(
            Color(0xFF229799), // Tu teal principal
            Color(0xFF4DD0E1), // Un aqua vibrante
            Color(0xFF81D4FA), // Un azul clarito
            Color(0xFF00ACC1)  // Un cyan más profundo
        ),
        start = Offset(moverX, moverY),
        end = Offset(moverX + 800f, moverY + 800f)
    )

    var rolSeleccionado by remember { mutableStateOf<RolUsuario?>(null) }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    // Variable para saber si mostramos el video o no
    var mostrarAyuda by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradienteFondo) // AQUÍ APLICAMOS EL FONDO ANIMADO
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Botón de ayuda en la esquina superior derecha
        IconButton(
            onClick = { mostrarAyuda = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info, // Usamos Info para que no te dé error
                contentDescription = "Ver Ayuda",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            // Le subimos un poquito la sombra (elevation) para que resalte más contra el gradiente
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FixUAM",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorPrincipal
                )
                Text(
                    text = "Reportes Inteligentes",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(40.dp))

                Crossfade(
                    targetState = rolSeleccionado,
                    animationSpec = tween(durationMillis = 500),
                    label = "AnimacionLogin"
                ) { estadoRol ->
                    if (estadoRol == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "¿Cómo deseás ingresar?",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                            BotonRol("Soy Docente", colorPrincipal) { rolSeleccionado = RolUsuario.DOCENTE }
                            Spacer(modifier = Modifier.height(12.dp))
                            BotonRol("Soy Colaborador", colorPrincipal) { rolSeleccionado = RolUsuario.COLABORADOR }
                            Spacer(modifier = Modifier.height(12.dp))
                            BotonRol("Soy Administrador", Color.DarkGray) { rolSeleccionado = RolUsuario.ADMINISTRADOR }
                        }
                    } else {
                        Column {
                            TextButton(
                                onClick = {
                                    rolSeleccionado = null
                                    mensajeError = ""
                                },
                                modifier = Modifier.align(Alignment.Start)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = colorPrincipal)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Atrás", color = colorPrincipal)
                            }

                            Text(
                                text = "Ingresar como ${estadoRol.name.lowercase().replaceFirstChar { it.uppercase() }}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedTextField(
                                value = correo,
                                onValueChange = { correo = it },
                                label = { Text("Correo institucional") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = contrasena,
                                onValueChange = { contrasena = it },
                                label = { Text("Contraseña") },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation(),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp)
                            )

                            if (mensajeError.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = {
                                    if (correo.isBlank() || contrasena.isBlank()) {
                                        mensajeError = "Llená todos los campos, perro"
                                    } else {
                                        mensajeError = ""
                                        // TRUCAZO: Agarramos lo que está antes del @ para usarlo de nombre
                                        val nombreExtraido = correo.substringBefore("@")
                                            .replace(".", " ") // Cambia los puntos por espacios
                                            .split(" ")
                                            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } } // Pone mayúsculas

                                        // Guardamos el nombre en la variable global
                                        usuarioActualLogueado = nombreExtraido.ifEmpty { "Usuario UAM" }

                                        alIniciarSesion(estadoRol)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = colorPrincipal),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text("Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
        // Ventana flotante que se abre al tocar el botón
        if (mostrarAyuda) {
            Dialog(onDismissRequest = { mostrarAyuda = false }) {
                Card(
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.Info, contentDescription = null, tint = Color.White, modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                    // AQUÍ LLAMAMOS AL VIDEO
                                    ReproductorVideoTutorial(modifier = Modifier.fillMaxWidth().height(250.dp))

                                    Spacer(modifier = Modifier.height(24.dp))
                                    Button(
                                        onClick = { mostrarAyuda = false },
                                        colors = ButtonDefaults.buttonColors(containerColor = colorPrincipal)
                                    ) {
                                        Text("Cerrar Ayuda")
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BotonRol(texto: String, colorFondo: Color, alHacerClic: () -> Unit) {
    Button(
        onClick = alHacerClic,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = texto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun ReproductorVideoTutorial(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Inicializamos el reproductor
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // Aquí llamamos a tu video. ¡Asegurate de que se llame video_ayuda.mp4!
            val uri = Uri.parse("android.resource://${context.packageName}/raw/video_ayuda")
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = Player.REPEAT_MODE_ALL // Bucle infinito
            playWhenReady = true // Que arranque solito
            prepare()
        }
    }

    // Limpiamos la memoria cuando se cierre la ventana para que no quede sonando de fondo
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Adaptamos el reproductor de Android normal a Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false // Escondemos los botones de pausa/play para que parezca un GIF
            }
        },
        modifier = modifier
    )
}

