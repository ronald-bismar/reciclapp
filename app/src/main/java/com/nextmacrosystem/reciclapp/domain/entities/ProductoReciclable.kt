package com.nextmacrosystem.reciclapp.domain.entities

data class ProductoReciclable(
    var idProducto: String = "",
    var nombreProducto: String = "",
    var detallesProducto: String = "",
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
    var idVendedor: String = "",
    var idComprador: String? = "",
    var idCategoria: String = "",
    var emisionCO2Kilo: Double = 0.0,
    var pesoPorUnidad: Double = 0.0
) {
    // Constructor sin argumentos necesario para Firebase Firestore
    constructor() : this("", "", "", "", 0.0, "", "", 0, "", "", "Bs", "", 0, 0, false, "0", "0", "", 0.0, 0.0)
}