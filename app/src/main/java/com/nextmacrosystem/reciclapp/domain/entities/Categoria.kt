<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/Categoria.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/Categoria.kt

data class Categoria(
    val idCategoria: String = "",
    val nombre: String = "",
    val unidadDeMedida: String = "",
    val puntosPorTransaccion: Int = 0,
    val factorContaminacion: Double = 0.0, // Nuevo atributo
    val descripcionCategoria: String = "",
) {
    fun calcularPuntosTransaccion(categoria: Categoria, cantidad: Double, bonus: Int = 0): Int {
        val puntosBase = categoria.puntosPorTransaccion
        val factor = categoria.factorContaminacion
        val puntosCalculados = (puntosBase * factor * cantidad).toInt()
        return puntosCalculados + bonus
    }

}
