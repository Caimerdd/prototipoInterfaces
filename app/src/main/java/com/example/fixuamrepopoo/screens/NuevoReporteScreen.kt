package com.example.fixuamrepopoo.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fixuamrepopoo.screens.Reporte

import com.example.fixuamrepopoo.ui.theme.ColorGrisTexto
import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorRojo
import com.example.fixuamrepopoo.ui.theme.ColorTexto
@Composable
fun NuevoReporteScreen(
    volver: () -> Unit,
    continuar: (Reporte) -> Unit
) {
    var tipoSeleccionado by remember { mutableStateOf("Internet / Wi-Fi") }
    var aula by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf("") }
    var fotoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var error by remember { mutableStateOf("") }

    val selectorFotoGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            fotoUri = uri.toString()
            fotoBitmap = null
        }
    }

    val tomarFotoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotoBitmap = bitmap
            fotoUri = ""
        }
    }

    val tipos = listOf(
        "Computadora",
        "Proyector",
        "Pantalla interactiva",
        "Internet / Wi-Fi",
        "Cable HDMI",
        "Accesorios",
        "Otro"
    )

    FondoPrincipal {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
                .verticalScroll(rememberScrollState())
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
                text = "Nuevo reporte",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Text(
                text = "Selecciona el tipo de problema",
                color = ColorGrisTexto
            )

            Spacer(modifier = Modifier.height(18.dp))

            tipos.forEach { tipo ->
                OpcionProblema(
                    texto = tipo,
                    seleccionado = tipo == tipoSeleccionado,
                    onClick = {
                        tipoSeleccionado = tipo
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = aula,
                onValueChange = {
                    aula = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Salón o aula donde está la falla")
                },
                placeholder = {
                    Text("Ej: A201, B105, C302")
                },
                shape = RoundedCornerShape(18.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                label = {
                    Text("Describe el problema")
                },
                placeholder = {
                    Text("Ej: No hay conexión a internet en el aula.")
                },
                shape = RoundedCornerShape(18.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            TarjetaBlanca(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Foto del problema",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when {
                        fotoBitmap != null -> "Foto tomada con la cámara."
                        fotoUri.isNotBlank() -> "Foto seleccionada desde la galería."
                        else -> "Podés tomar una foto o elegir una imagen como evidencia."
                    },
                    color = if (fotoBitmap != null || fotoUri.isNotBlank()) {
                        ColorPrincipal
                    } else {
                        ColorGrisTexto
                    }
                )

                if (fotoBitmap != null) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Image(
                        bitmap = fotoBitmap!!.asImageBitmap(),
                        contentDescription = "Foto tomada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                BotonPrincipal(
                    texto = "Tomar foto",
                    onClick = {
                        tomarFotoCamara.launch(null)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                BotonOscuro(
                    texto = "Elegir de galería",
                    onClick = {
                        selectorFotoGaleria.launch("image/*")
                    }
                )
            }

            if (error.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = error,
                    color = ColorRojo,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            BotonPrincipal(
                texto = "Continuar",
                onClick = {
                    if (aula.isBlank() || descripcion.isBlank()) {
                        error = "Completá el aula y la descripción del problema"
                    } else {
                        val nuevoReporte = Reporte(
                            id = (100..999).random(),
                            docente = "Juan Pérez",
                            tipo = tipoSeleccionado,
                            aula = aula,
                            descripcion = descripcion,
                            fecha = "01/05/2026 21:51",
                            prioridad = "Media",
                            estado = "Pendiente",
                            fotoUri = fotoUri,
                            fotoBitmap = fotoBitmap
                        )

                        continuar(nuevoReporte)
                    }
                }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun OpcionProblema(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = if (seleccionado) 2.dp else 1.dp,
            color = if (seleccionado) ColorPrincipal else Color(0xFFE0E0E0)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = texto,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = ColorTexto,
                fontSize = 17.sp
            )

            RadioButton(
                selected = seleccionado,
                onClick = onClick
            )
        }
    }
}