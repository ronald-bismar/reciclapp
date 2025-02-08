package com.example.reciclapp.domain.entities

data class Categoria(
    val idCategoria: String = "",
    val nombre: String = "",
    val unidadDeMedida: String = "",
    val puntosPorTransaccion: Int = 0,
    val factorContaminacion: Double = 0.0, // Nuevo atributo
    val descripcionCategoria: String = "",
    val productosDeCategoria: MutableList<String>
) {
    fun calcularPuntosTransaccion(categoria: Categoria, cantidad: Double, bonus: Int = 0): Int {
        val puntosBase = categoria.puntosPorTransaccion
        val factor = categoria.factorContaminacion
        val puntosCalculados = (puntosBase * factor * cantidad).toInt()
        return puntosCalculados + bonus
    }

}
