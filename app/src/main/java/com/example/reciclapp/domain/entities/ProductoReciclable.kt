package com.example.reciclapp.domain.entities

data class ProductoReciclable(
    var idProducto: Int = 0,
    var nombreProducto: String = "",
    var descripcionProducto: String = "",
    var urlImagenProducto: String = "",
    var precio: Double = 0.0,
    var fechaPublicacion: String = "", // Fecha de publicación del producto
    var fechaModificacion: String = "",
    var cantidad: Int = 0, // Cantidad disponible en stock
    var categoria: String = "", // Categoría del producto
    var ubicacionProducto: String = "", // Ubicación del producto
    var monedaDeCompra: String = "Bs",
    var unidadMedida: String = "", // Unidad de medida (por kilo, por unidad, etc.)
    var puntosPorCompra: Int = 0,
    var meGusta: Int = 0,
    var fueVendida: Boolean = false,
    var idUsuario: Int = 0,
    var idCategoria: Int = 0
) {
    // Constructor sin argumentos necesario para Firebase Firestore
    constructor() : this(0, "", "", "", 0.0, "", "", 0, "", "", "Bs", "",0 , 0, false, 0, 0)
}