package com.example.fixuamrepopoo.screens

import android.graphics.Bitmap

data class Reporte(
    val id: Int,
    val docente: String,
    val tipo: String,
    val aula: String,
    val descripcion: String,
    val fecha: String,
    val prioridad: String,
    val estado: String,
    val atendidoPor: String = "",
    val fotoUri: String = "",
    val fotoBitmap: Bitmap? = null
)