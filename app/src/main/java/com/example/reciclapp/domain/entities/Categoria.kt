package com.example.reciclapp.domain.entities

data class Categoria(
    var idCategoria: Int = 0,
    var nombre: String = "",
    var unidadDeMedida: Int = 0,
    var puntosPorTransaccion: Int = 0,
    var descripcionCategoria: String = ""
)