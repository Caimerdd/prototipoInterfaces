package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                BotonCambioTema()
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
                        onClick = {
                            seleccionarRol("docente")
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    BotonPrincipal(
                        texto = "Soy Colaborador",
                        onClick = {
                            seleccionarRol("colaborador")
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    BotonOscuro(
                        texto = "Soy Administrador",
                        onClick = {
                            seleccionarRol("admin")
                        }
                    )
                }
            }
        }
    }
}