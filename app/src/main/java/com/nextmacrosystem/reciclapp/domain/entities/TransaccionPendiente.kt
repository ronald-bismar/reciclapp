<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/TransaccionPendiente.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/TransaccionPendiente.kt

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

