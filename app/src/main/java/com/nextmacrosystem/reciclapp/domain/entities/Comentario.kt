<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/Comentario.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/Comentario.kt

data class Comentario(
    var idComentario: String = "",
    var idUsuarioQueComento: String = "",
    var nombreUsuarioQueComento: String = "",
    var contenidoComentario: String = "",
    var fechaComentario: String = "",
    var puntuacion: Int = 0,
    var idUsuarioComentado: String = "",
    var cantMeGusta: Int = 0
) {
    // Constructor sin argumentos necesario para Firebase Firestore
    constructor() : this("", "", "", "", "", 0, "", 0)
}