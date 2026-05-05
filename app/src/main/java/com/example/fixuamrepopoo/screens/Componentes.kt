package com.example.fixuamrepopoo.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.fixuamrepopoo.ui.theme.LocalConfiguracionTema
import com.example.fixuamrepopoo.ui.theme.colorPrincipalApp
import com.example.fixuamrepopoo.ui.theme.fondoApp
import com.example.fixuamrepopoo.ui.theme.tarjetaApp
import com.example.fixuamrepopoo.ui.theme.textoApp
import com.example.fixuamrepopoo.ui.theme.textoSecundarioApp
@Composable
fun FondoPrincipal(
    content: @Composable BoxScope.() -> Unit
) {
    val config = LocalConfiguracionTema.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoApp(config.modoOscuro)),
        content = content
    )
}

@Composable
fun FondoDegradadoAnimado(
    content: @Composable BoxScope.() -> Unit
) {
    val config = LocalConfiguracionTema.current

    val transicion = rememberInfiniteTransition(label = "fondo_animado")

    val movimiento by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "movimiento"
    )

    val color1 = if (config.modoOscuro) Color(0xFF061719) else Color(0xFF9BE7EA)
    val color2 = if (config.modoOscuro) Color(0xFF103B40) else Color(0xFF32A0A6)
    val color3 = if (config.modoOscuro) Color(0xFF081F22) else Color(0xFF7ADDE2)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(color1, color2, color3),
                    start = androidx.compose.ui.geometry.Offset(
                        x = movimiento * 900f,
                        y = 0f
                    ),
                    end = androidx.compose.ui.geometry.Offset(
                        x = 0f,
                        y = movimiento * 1200f
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = 80.dp)
                .alpha(0.25f)
                .background(Color.White, CircleShape)
        )

        Box(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-70).dp, y = (-80).dp)
                .alpha(0.18f)
                .background(Color.White, CircleShape)
        )

        content()
    }
}

@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorPrincipalApp(),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BotonOscuro(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF444444),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TarjetaBlanca(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val config = LocalConfiguracionTema.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = tarjetaApp(config.modoOscuro)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content
        )
    }
}

@Composable
fun OpcionMenu(
    titulo: String,
    subtitulo: String,
    letra: String,
    onClick: () -> Unit
) {
    val config = LocalConfiguracionTema.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = tarjetaApp(config.modoOscuro)
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(colorPrincipalApp().copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letra,
                    color = colorPrincipalApp(),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    color = textoApp(config.modoOscuro),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitulo,
                    color = textoSecundarioApp(config.modoOscuro)
                )
            }

            Text(
                text = ">",
                color = colorPrincipalApp(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BotonCambioTema() {
    val config = LocalConfiguracionTema.current

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (config.modoOscuro) "Oscuro" else "Claro",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = config.modoOscuro,
            onCheckedChange = {
                config.cambiarModo()
            }
        )
    }
}