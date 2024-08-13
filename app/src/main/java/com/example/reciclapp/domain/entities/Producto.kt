package com.example.reciclapp.domain.entities

data class Producto(
    val idProducto: Int = 0,
    val nombreProducto: String = "",
    val descripcionProducto: String = "",
    val urlImagenProducto: String = "",
    val precio: Double = 0.0,
    val fechaPublicacion: String = "", // Fecha de publicación del producto
    val cantidad: Int = 0, // Cantidad disponible en stock
    val categoria: String = "", // Categoría del producto
    val ubicacionProducto: String = "", // Ubicación del producto
    val monedaDeCompra: String = "Bs",
    val unidadMedida: String = "", // Unidad de medida (por kilo, por unidad, etc.)
    val puntos: Int = 0,
    val fueVendida: Boolean = false,
    val idVendedor: Int = 0
) {
    // Constructor sin argumentos necesario para Firebase Firestore
    constructor() : this(0, "", "", "", 0.0, "", 0, "", "", "Bs", "", 0,false, 0)
}
