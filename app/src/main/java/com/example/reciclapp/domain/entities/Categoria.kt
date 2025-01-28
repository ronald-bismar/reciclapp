package com.example.reciclapp.domain.entities

data class Categoria(
    var idCategoria: Int = 0,
    var nombre: String = "",
    var unidadDeMedida: String = "",
    var puntosPorTransaccion: Int = 0,
    var descripcionCategoria: String = "",
    var productosDeCategoria: MutableList<String> = mutableListOf()
)