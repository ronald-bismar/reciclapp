<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/UbicacionGPS.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/UbicacionGPS.kt

data class UbicacionGPS (
    val idUbicacionGPS: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val precision: Float = 0.0f,
    val fechaRegistro: String = "",
    val idUsuario: String = ""
)