package com.example.fixuamrepopoo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fixuamrepopoo.ui.theme.ColorGrisTexto
import com.example.fixuamrepopoo.ui.theme.ColorPrincipal
import com.example.fixuamrepopoo.ui.theme.ColorTexto

@Composable
fun PerfilDocenteScreen(
    volver: () -> Unit,
    cerrarSesion: () -> Unit
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

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Mi perfil",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Spacer(modifier = Modifier.height(22.dp))

            TarjetaBlanca(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .background(ColorPrincipal, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "JP",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column {
                        Text(
                            text = "Juan Pérez",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorTexto
                        )

                        Text(
                            text = "Docente UAM",
                            fontSize = 17.sp,
                            color = ColorGrisTexto
                        )

                        Text(
                            text = "jperez@uam.edu.ni",
                            fontSize = 16.sp,
                            color = ColorGrisTexto
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            TarjetaBlanca(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Información académica",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Departamento: Ingeniería",
                    fontSize = 17.sp,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Cargo: Docente",
                    fontSize = 17.sp,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Rol en la app: Reportar incidencias tecnológicas",
                    fontSize = 17.sp,
                    color = ColorTexto
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            TarjetaBlanca(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Uso de la aplicación",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Desde este perfil el docente puede reportar fallas tecnológicas dentro del aula, consultar el estado de sus reportes y verificar si ya fueron atendidos por soporte.",
                    fontSize = 16.sp,
                    color = ColorGrisTexto
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            BotonOscuro(
                texto = "Cerrar sesión",
                onClick = cerrarSesion
            )

            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}
