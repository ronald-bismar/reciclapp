package com.example.reciclapp.domain.entities

data class TransaccionPendiente(
    val idTransaccion: String = "",
    val idsProductos: String = "", // Separados por comas
    val idVendedor: String = "",
    val idComprador: String = "",
    val fechaCreacion: String = "",
    val codigoQR: String = "", // Contendrá la información codificada
    val estado: EstadoTransaccion = EstadoTransaccion.PENDIENTE,
    val puntosComprador: Int = 0,
    val puntosAmbosUsuarios: Int = 0
)

