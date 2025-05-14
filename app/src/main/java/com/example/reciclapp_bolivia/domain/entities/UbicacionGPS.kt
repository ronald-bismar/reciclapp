package com.example.reciclapp_bolivia.domain.entities

data class UbicacionGPS (
    val idUbicacionGPS: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val precision: Float = 0.0f,
    val fechaRegistro: String = "",
    val idUsuario: String = ""
)