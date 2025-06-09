package com.nextmacrosystem.reciclapp.util

object NombreNivelUsuario {

    fun obtenerNombreNivel(puntaje: Int): String {
        return when (puntaje) {
            in 0..500 -> "Reciclador Novato"
            in 501..1000 -> "Reciclador Comprometido"
            in 1001..1500 -> "Reciclador Experto"
            in 1501..2000 -> "EcoGuerrero"
            in 2001..3000 -> "EcoGuerrero Maestro"
            in 3001..4000 -> "Héroe del Reciclaje"
            in 4001..5000 -> "Guardian del Planeta"
            else -> "Sin nivel" // Para puntajes fuera del rango esperado
        }
    }

    fun obtenerNivel(puntaje: Int): String {
        return when (puntaje) {
            in 0..500 -> "Nivel 1"
            in 501..1000 -> "Nivel 2"
            in 1001..1500 -> "Nivel 3"
            in 1501..2000 -> "Nivel 4"
            in 2001..3000 -> "Nivel 5"
            in 3001..4000 -> "Nivel 6"
            in 4001..5000 -> "Nivel 7"
            else -> "Sin nivel" // Para puntajes fuera del rango esperado
        }
    }

    // Función para calcular el progreso hacia el siguiente nivel
    fun calcularProgreso(puntaje: Int): Pair<Int, String> {
        val rangos = listOf(
            0 to 500,    // Nivel 1
            501 to 1000, // Nivel 2
            1001 to 1500, // Nivel 3
            1501 to 2000, // Nivel 4
            2001 to 3000, // Nivel 5
            3001 to 4000, // Nivel 6
            4001 to 5000  // Nivel 7
        )

        // Encontrar el rango actual del usuario
        val rangoActual = rangos.find { puntaje in it.first..it.second }

        return if (rangoActual != null) {
            val (inicioRango, finRango) = rangoActual
            val progreso = ((puntaje - inicioRango).toDouble() / (finRango - inicioRango)) * 100

            // Obtener el nombre del siguiente nivel
            val siguienteNivel = when (finRango) {
                500 -> "Nivel 2"
                1000 -> "Nivel 3"
                1500 -> "Nivel 4"
                2000 -> "Nivel 5"
                3000 -> "Nivel 6"
                4000 -> "Nivel 7"
                5000 -> "Nivel 7" // No hay siguiente nivel después del 7
                else -> "Sin nivel"
            }

            Pair(progreso.toInt(), siguienteNivel) // Devuelve el porcentaje recorrido y el nombre del siguiente nivel
        } else {
            Pair(0, "Sin nivel") // Si el puntaje está fuera de los rangos definidos
        }
    }
}