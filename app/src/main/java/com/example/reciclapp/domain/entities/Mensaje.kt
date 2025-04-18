package com.example.reciclapp.domain.entities

data class Mensaje(
    var idMensaje: String = "",
    var contenido: String = "",
    var idProductoConPrecio: String = "", //Formato: "idProducto:precio,idProducto:precio"
    var idComprador: String = "",
    var idVendedor: String = "",
    var mensajeNuevo: Boolean = true
)