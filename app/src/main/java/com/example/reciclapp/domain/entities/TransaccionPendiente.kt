package com.example.reciclapp.domain.entities

data class TransaccionPendiente(
    val idTransaccion: String = "",
    val idProducto: String = "",
    val idVendedor: String = "",
    val idComprador: String = "",
    val fechaCreacion: String = "",
    val codigoQR: String = "", // Contendrá la información codificada
    val estado: EstadoTransaccion = EstadoTransaccion.PENDIENTE
)

enum class EstadoTransaccion {
    PENDIENTE,
    COMPLETADA,
    CANCELADA
}