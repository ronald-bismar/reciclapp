<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/Logro.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/Logro.kt

import androidx.compose.ui.graphics.vector.ImageVector

data class Logro(
    var idLogro: String,
    var titulo: String,
    var descripcion: String,
    var badge: ImageVector, // Icono del logro
    var mensajeFelicitacion: String,
    var esCompletado: Boolean = false // Indica si el logro ha sido completado
)