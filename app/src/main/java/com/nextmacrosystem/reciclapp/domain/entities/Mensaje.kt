package com.nextmacrosystem.reciclapp.domain.entities

data class Mensaje(
    var idMensaje: String = "",
    var contenido: String = "",
    var idProductoConPrecio: String = "", //Formato: "idProducto:precio,idProducto:precio,idProducto:precio"
    var idEmisor: String = "",
    var idReceptor: String = "",
    var isNewMessage: Boolean = true,
    var titleMessage: String = "",
    var idTransaccion: String = "", //Para traer solo los mensajes de una transaccion
    var fecha: String = "",
    var idChat: String = ""
)