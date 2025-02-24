package com.example.reciclapp.util

import com.example.reciclapp.domain.entities.ProductoReciclable
import kotlin.math.roundToInt

object ImpactoAmbientalUtil {
    // Fuente de investigación:
    // 1. EPA (Agencia de Protección Ambiental de EE.UU.)
    //    - 1 árbol adulto absorbe aproximadamente 21.77 kg de CO₂ al año
    //    - https://www.epa.gov/energy/greenhouse-gas-equivalencies-calculator
    // 2. Estudios de huella de carbono de materiales reciclables
    private const val CO2_POR_ARBOL_ANUAL = 21.77

    fun calcularArbolesSalvados(transacciones: List<ProductoReciclable>): Int {
        return try {
            val totalCO2 = transacciones.sumOf {
                it.cantidad * it.pesoPorUnidad * it.emisionCO2Kilo
            }
            (totalCO2 / CO2_POR_ARBOL_ANUAL).roundToInt()
        } catch (e: Exception) {
            0 // Evita crashes por datos corruptos
        }
    }
}