package com.example.reciclapp_bolivia.domain.entities

import androidx.compose.ui.graphics.vector.ImageVector

data class Logro(
    var idLogro: String,
    var titulo: String,
    var descripcion: String,
    var badge: ImageVector, // Icono del logro
    var mensajeFelicitacion: String,
    var esCompletado: Boolean = false // Indica si el logro ha sido completado
)