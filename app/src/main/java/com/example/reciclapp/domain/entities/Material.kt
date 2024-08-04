package com.example.reciclapp.domain.entities

data class Material(
    val idMaterial: Int = 0,
    val urlImagenMaterial: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 0,
    val unidadDeMedida: String = "",
    val monedaDeCompra: String = "Bs",
    val puntos: Int = 0,
    val idComprador: Int = 0
) {
    // Constructor sin argumentos necesario para Firebase Firestore
    constructor() : this(0, "", "", "", 0.0, 0, "", "Bs", 0, 0)
}
