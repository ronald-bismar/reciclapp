package com.example.reciclapp.domain.entities

data class Categoria(
    val idCategoria: Int,
    val nombre: String,
    val unidadDeMedida: String,
    val puntosPorTransaccion: Int,
    val factorContaminacion: Double, // Nuevo atributo
    val descripcionCategoria: String,
    val productosDeCategoria: MutableList<String>
) {
    fun calcularPuntosTransaccion(categoria: Categoria, cantidad: Double, bonus: Int = 0): Int {
        val puntosBase = categoria.puntosPorTransaccion
        val factor = categoria.factorContaminacion
        val puntosCalculados = (puntosBase * factor * cantidad).toInt()
        return puntosCalculados + bonus
    }

}
