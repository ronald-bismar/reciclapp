package com.example.reciclapp.domain.entities

data class UbicacionGPS (
    val idUbicacionGPS: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val precision: Float = 0.0f,
    val fechaRegistro: String = "",
    val idUsuario: Int = 0
)