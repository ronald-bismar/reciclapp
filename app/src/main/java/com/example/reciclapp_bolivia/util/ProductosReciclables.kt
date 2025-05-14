package com.example.reciclapp_bolivia.util

import android.util.Log
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable

// TODO: Realizar la investigación más precisa para los pesos por unidad
object ProductosReciclables {
    val PET = ProductoReciclable(nombreProducto = "PET", emisionCO2Kilo = 3.5, idCategoria = "1", pesoPorUnidad = 0.025)
    val HDPE = ProductoReciclable(nombreProducto = "HDPE", emisionCO2Kilo = 3.5, idCategoria = "1", pesoPorUnidad = 0.05)
    val PVC = ProductoReciclable(nombreProducto = "PVC", emisionCO2Kilo = 3.5, idCategoria = "1", pesoPorUnidad = 0.1)
    val LDPE = ProductoReciclable(nombreProducto = "LDPE", emisionCO2Kilo = 3.5, idCategoria = "1", pesoPorUnidad = 0.02)
    val PP = ProductoReciclable(nombreProducto = "PP", emisionCO2Kilo = 3.5, idCategoria = "1", pesoPorUnidad = 0.04)
    val PS = ProductoReciclable(nombreProducto = "PS", emisionCO2Kilo = 3.5, idCategoria = "1", pesoPorUnidad = 0.03)
    val Aluminio = ProductoReciclable(nombreProducto = "Aluminio", emisionCO2Kilo = 8.24, idCategoria = "2", pesoPorUnidad = 0.015)
    val Acero = ProductoReciclable(nombreProducto = "Acero", emisionCO2Kilo = 1.37, idCategoria = "2", pesoPorUnidad = 0.02)
    val Cobre = ProductoReciclable(nombreProducto = "Cobre", emisionCO2Kilo = 2.6, idCategoria = "2", pesoPorUnidad = 0.03)
    val Laton = ProductoReciclable(nombreProducto = "Latón", emisionCO2Kilo = 3.8, idCategoria = "2", pesoPorUnidad = 0.025)
    val PapelBlanco = ProductoReciclable(nombreProducto = "Papel Blanco", emisionCO2Kilo = 3.5, idCategoria = "3", pesoPorUnidad = 0.005)
    val PapelPeriodico = ProductoReciclable(nombreProducto = "Papel Periódico", emisionCO2Kilo = 3.5, idCategoria = "3", pesoPorUnidad = 0.006)
    val CartonCorrugado = ProductoReciclable(nombreProducto = "Cartón Corrugado", emisionCO2Kilo = 3.5, idCategoria = "3", pesoPorUnidad = 0.01)
    val VidrioTransparente = ProductoReciclable(nombreProducto = "Vidrio Transparente", emisionCO2Kilo = 0.85, idCategoria = "4", pesoPorUnidad = 0.4)
    val VidrioVerde = ProductoReciclable(nombreProducto = "Vidrio Verde", emisionCO2Kilo = 0.85, idCategoria = "4", pesoPorUnidad = 0.4)
    val VidrioAmbar = ProductoReciclable(nombreProducto = "Vidrio Ámbar", emisionCO2Kilo = 0.85, idCategoria = "4", pesoPorUnidad = 0.4)
    val RestosDeAlimentos = ProductoReciclable(nombreProducto = "Restos de Alimentos", emisionCO2Kilo = 0.45, idCategoria = "5", pesoPorUnidad = 0.2)
    val RestosDeJardineria = ProductoReciclable(nombreProducto = "Restos de Jardinería", emisionCO2Kilo = 0.35, idCategoria = "5", pesoPorUnidad = 0.3)
    val Algodon = ProductoReciclable(nombreProducto = "Algodón", emisionCO2Kilo = 4.12, idCategoria = "6", pesoPorUnidad = 0.25)
    val Poliester = ProductoReciclable(nombreProducto = "Poliéster", emisionCO2Kilo = 4.2, idCategoria = "6", pesoPorUnidad = 0.2)
    val Mezclilla = ProductoReciclable(nombreProducto = "Mezclilla", emisionCO2Kilo = 6.0, idCategoria = "6", pesoPorUnidad = 0.5)
    val Lana = ProductoReciclable(nombreProducto = "Lana", emisionCO2Kilo = 5.25, idCategoria = "6", pesoPorUnidad = 0.3)
    val Celulares = ProductoReciclable(nombreProducto = "Celulares", emisionCO2Kilo = 55.0, idCategoria = "7", pesoPorUnidad = 0.15)
    val Computadoras = ProductoReciclable(nombreProducto = "Computadoras", emisionCO2Kilo = 137.5, idCategoria = "7", pesoPorUnidad = 3.0)
    val Electrodomesticos = ProductoReciclable(nombreProducto = "Electrodomésticos", emisionCO2Kilo = 22.75, idCategoria = "7", pesoPorUnidad = 15.0)
    val Pallets = ProductoReciclable(nombreProducto = "Pallets", emisionCO2Kilo = 0.375, idCategoria = "8", pesoPorUnidad = 20.0)
    val RestosDeMuebles = ProductoReciclable(nombreProducto = "Restos de Muebles", emisionCO2Kilo = 0.4, idCategoria = "8", pesoPorUnidad = 25.0)
    val MaderaTratada = ProductoReciclable(nombreProducto = "Madera Tratada", emisionCO2Kilo = 0.68, idCategoria = "8", pesoPorUnidad = 15.0)
    val Lamparas = ProductoReciclable(nombreProducto = "Lámparas", emisionCO2Kilo = 3.33, idCategoria = "9", pesoPorUnidad = 0.2)
    val Baterias = ProductoReciclable(nombreProducto = "Baterías", emisionCO2Kilo = 11.6, idCategoria = "9", pesoPorUnidad = 0.03)
    val Neumaticos = ProductoReciclable(nombreProducto = "Neumáticos", emisionCO2Kilo = 4.33, idCategoria = "9", pesoPorUnidad = 8.0)
    val Ceramicas = ProductoReciclable(nombreProducto = "Cerámicas", emisionCO2Kilo = 1.1, idCategoria = "9", pesoPorUnidad = 0.5)

    val productosPredeterminados = listOf(
        PET, HDPE, PVC, LDPE, PP, PS, Aluminio, Acero, Cobre, Laton, PapelBlanco, PapelPeriodico,
        CartonCorrugado, VidrioTransparente, VidrioVerde, VidrioAmbar, RestosDeAlimentos,
        RestosDeJardineria, Algodon, Poliester, Mezclilla, Lana, Celulares, Computadoras,
        Electrodomesticos, Pallets, RestosDeMuebles, MaderaTratada, Lamparas, Baterias,
        Neumaticos, Ceramicas
    )

    // Función para obtener el total de puntos por categoría
    fun obtenerPuntosPorCategoria(): Map<String, Int> {
        // Mapa para almacenar el total de puntos por categoría
        val puntosPorCategoria = mutableMapOf<String, Int>()

        // Recorrer todos los productos
        productosPredeterminados.forEach { producto ->
            // Sumar los puntos de cada producto a su categoría correspondiente
            puntosPorCategoria[producto.idCategoria] = puntosPorCategoria.getOrDefault(producto.idCategoria, 0) + producto.puntosPorCompra
        }

        return puntosPorCategoria
    }

    // Función para obtener el nombre de la categoría y el total de puntos
    fun obtenerNombreYPuntosPorCategoria(productos : List<ProductoReciclable>): Map<String, Int> {
        // Mapa para almacenar el nombre de la categoría y el total de puntos
        val nombreYPuntosPorCategoria = mutableMapOf<String, Int>()

        // Recorrer todos los productos
        productos.forEach { producto ->
            // Obtener el nombre de la categoría (puedes usar un mapa de categorías si lo tienes)
            val nombreCategoria = when (producto.idCategoria) {
                "1" -> "Plásticos"
                "2" -> "Metales"
                "3" -> "Papel y Cartón"
                "4" -> "Vidrio"
                "5" -> "Orgánicos"
                "6" -> "Textiles"
                "7" -> "Electrónicos"
                "8" -> "Madera"
                "9" -> "Otros"
                else -> "Desconocido"
            }
            // Sumar los puntos de cada producto a su categoría correspondiente
            nombreYPuntosPorCategoria[nombreCategoria] = nombreYPuntosPorCategoria.getOrDefault(nombreCategoria, 0) + producto.puntosPorCompra
        }

        Log.d("Puntaje","nombre: $nombreYPuntosPorCategoria")
        return nombreYPuntosPorCategoria
    }
}
