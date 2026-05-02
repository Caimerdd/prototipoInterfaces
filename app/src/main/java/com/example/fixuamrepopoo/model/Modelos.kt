package com.example.fixuamrepopoo.model

// Los roles de los usuarios
enum class RolUsuario { ADMINISTRADOR, DOCENTE, COLABORADOR }

enum class EstadoReporte { PENDIENTE, EN_PROCESO, SOLUCIONADO }

data class Usuario(
    val identificador: String,
    val nombreCompleto: String,
    val rol: RolUsuario
)

//plantilla de reporte
data class Reporte(
    val identificadorReporte: String,
    val descripcionProblema: String,
    val edificio: String,
    val aula: String,
    val fechaHora: String,
    val nombreDocente: String,
    var nombreColaboradorAsignado: String? = null,
    var estadoActual: EstadoReporte = EstadoReporte.PENDIENTE // Por defecto nace como PENDIENTE
)