package com.nextmacrosystem.reciclapp.domain.entities

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