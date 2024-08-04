package com.example.reciclapp.domain.entities

data class Comentario(
    var idComentario: Int = 0,
    var idUsuarioQueComento: Int = 0,
    var nombreUsuarioQueComento: String = "",
    var contenidoComentario: String = "",
    var fechaComentario: String = "",
    var puntuacion: Int = 0,
    var idUsuarioComentado: Int = 0
) {
    // Constructor sin argumentos necesario para Firebase Firestore
    constructor() : this(0, 0, "", "", "", 0, 0)
}
