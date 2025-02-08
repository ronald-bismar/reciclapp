package com.example.reciclapp.domain.entities

data class Usuario(
    val idUsuario: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val telefono: Long = 0L,
    val correo: String = "",
    val contrasena: String = "",
    val direccion: String = "",
    var urlImagenPerfil: String = "",
    var tipoDeUsuario: String = "", // Comprador o Vendedor
    val puntaje: Int = 0,
)
