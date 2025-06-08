<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/Mensaje.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/Mensaje.kt

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