package com.example.fixuamrepopoo.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlin.random.Random
import com.example.fixuamrepopoo.ui.theme.LocalConfiguracionTema
import com.example.fixuamrepopoo.ui.theme.colorPrincipalApp
import com.example.fixuamrepopoo.ui.theme.tarjetaApp
import com.example.fixuamrepopoo.ui.theme.textoApp
import com.example.fixuamrepopoo.ui.theme.textoSecundarioApp

@Composable
fun LoginScreen(
    seleccionarRol: (String) -> Unit
) {
    val config = LocalConfiguracionTema.current
    var mostrarAyuda by remember { mutableStateOf(false) }

    val listaVideos = listOf("video_ayuda1", "video_ayuda2", "video_ayuda3")

    // Guardamos el índice actual y el anterior para comparar
    var indiceActual by remember { mutableIntStateOf(0) }
    var ultimoIndice by remember { mutableIntStateOf(-1) }

    FondoDegradadoAnimado {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 24.dp)
            ) {
                IconButton(onClick = {
                    var nuevoIndice: Int
                    // Bucle para asegurar que el nuevo video no sea igual al último mostrado
                    do {
                        nuevoIndice = Random.nextInt(listaVideos.size)
                    } while (nuevoIndice == ultimoIndice)

                    indiceActual = nuevoIndice
                    ultimoIndice = nuevoIndice
                    mostrarAyuda = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Ayuda",
                        tint = colorPrincipalApp(),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                BotonCambioTema()
            }

            if (mostrarAyuda) {
                Dialog(onDismissRequest = { mostrarAyuda = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(420.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black),
                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Guía de Usuario",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Box(modifier = Modifier.weight(1f)) {
                                VideoPlayer(nombreVideo = listaVideos[indiceActual])
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { mostrarAyuda = false },
                                colors = ButtonDefaults.buttonColors(containerColor = colorPrincipalApp())
                            ) {
                                Text("Cerrar")
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.88f),
                shape = RoundedCornerShape(36.dp),
                colors = CardDefaults.cardColors(
                    containerColor = tarjetaApp(config.modoOscuro)
                ),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FixUAM",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrincipalApp()
                    )

                    Text(
                        text = "Reportes Inteligentes",
                        fontSize = 16.sp,
                        color = textoSecundarioApp(config.modoOscuro)
                    )

                    Spacer(modifier = Modifier.height(42.dp))

                    Text(
                        text = "¿Cómo deseas ingresar?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textoApp(config.modoOscuro)
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    BotonPrincipal(
                        texto = "Soy Docente",
                        onClick = { seleccionarRol("docente") }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    BotonPrincipal(
                        texto = "Soy Colaborador",
                        onClick = { seleccionarRol("colaborador") }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    BotonOscuro(
                        texto = "Soy Administrador",
                        onClick = { seleccionarRol("admin") }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(nombreVideo: String) {
    val context = LocalContext.current

    val exoPlayer = remember(nombreVideo) {
        ExoPlayer.Builder(context).build().apply {
            val uri = Uri.parse("android.resource://${context.packageName}/raw/$nombreVideo")
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(nombreVideo) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}