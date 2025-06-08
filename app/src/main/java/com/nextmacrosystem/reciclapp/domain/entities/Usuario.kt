<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/Usuario.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/Usuario.kt

data class Usuario(
    var idUsuario: String = "",
    var nombre: String = "",
    var apellido: String = "",
    var telefono: Long = 0L,
    var correo: String = "",
    var contrasena: String = "",
    var direccion: String = "",
    var urlImagenPerfil: String = "",
    var tipoDeUsuario: String = "", // Comprador o Vendedor
    var puntaje: Int = 0,
    var nombreNivel: String = "",
    var nivel: String = "", // Nivel 1, Nivel 2,Nivel 3...
    var logrosPorId: String = "", // "1, 2, 3"
    var tokenNotifications: String = ""
)