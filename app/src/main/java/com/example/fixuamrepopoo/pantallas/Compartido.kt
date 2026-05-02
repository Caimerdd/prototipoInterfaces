package com.example.uamfix.pantallas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.uamfix.model.Reporte

val colorPrincipal = Color(0xFF229799)
val colorFondoBase = Color(0xFFF0F4F4)

// 1. Lista de reportes que arranca VACÍA
val reportesGlobales = mutableStateListOf<Reporte>()

// 2. Variable global para saber quién inició sesión
var usuarioActualLogueado by mutableStateOf("")

