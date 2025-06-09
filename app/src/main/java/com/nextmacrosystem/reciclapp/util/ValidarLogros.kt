package com.nextmacrosystem.reciclapp.util

import android.util.Log
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ValidarLogros {
    fun validarRecicladorNovato(transacciones: List<ProductoReciclable>): Boolean {
        return transacciones.isNotEmpty()
    }

    fun validarRecicladorComprometido(transacciones: List<ProductoReciclable>): Boolean {
        return transacciones.size >= 10
    }

    fun validarRecicladorExperto(transacciones: List<ProductoReciclable>): Boolean {
        return transacciones.size >= 50
    }

    fun validarRecicladorMaster(transacciones: List<ProductoReciclable>): Boolean {
        return transacciones.size >= 100
    }

    fun validarRecordPersonal(transacciones: List<ProductoReciclable>): Boolean {
        val transaccionesPorDia = transacciones.groupBy { it.fechaPublicacion }
        return transaccionesPorDia.any { it.value.size >= 5 } // Ejemplo: 5 transacciones en un día
    }
    fun validarPuntosDorados(puntosTotales: Int): Boolean {
        Log.d("ValidarPuntos", "Puntos totales: $puntosTotales")
        return puntosTotales >= 1000
    }

    fun validarPuntosDePlatino(puntosTotales: Int): Boolean {
        Log.d("ValidarPuntos", "Puntos totales: $puntosTotales")
        return puntosTotales >= 5000
    }

    fun validarPuntosLegendarios(puntosTotales: Int): Boolean {
        Log.d("ValidarPuntos", "Puntos totales: $puntosTotales")
        return puntosTotales >= 10000
    }
    fun validarAmigoDelMedioAmbiente(transacciones: List<ProductoReciclable>): Boolean {
        val categoriasRecicladas = transacciones.map { it.categoria }.toSet()
        return categoriasRecicladas.size == ListOfCategorias.categorias.size
    }

    fun validarEspecialistaEnPlasticos(transacciones: List<ProductoReciclable>): Boolean {
        val puntosPlastico = transacciones
            .filter { it.categoria == "Plásticos" }
            .sumOf { it.puntosPorCompra }
        return puntosPlastico >= 1000
    }

    fun validarEspecialistaEnMetales(transacciones: List<ProductoReciclable>): Boolean {
        val puntosMetales = transacciones
            .filter { it.categoria == "Metales" }
            .sumOf { it.puntosPorCompra }
        return puntosMetales >= 1000
    }

    fun validarEspecialistaEnPapelYCarton(transacciones: List<ProductoReciclable>): Boolean {
        val puntosPapelCarton = transacciones
            .filter { it.categoria == "Papel y Cartón" }
            .sumOf { it.puntosPorCompra }
        return puntosPapelCarton >= 1000
    }

    fun validarEspecialistaEnVidrio(transacciones: List<ProductoReciclable>): Boolean {
        val puntosVidrio = transacciones
            .filter { it.categoria == "Vidrio" }
            .sumOf { it.puntosPorCompra }
        return puntosVidrio >= 1000
    }

    fun validarEspecialistaEnOrganicos(transacciones: List<ProductoReciclable>): Boolean {
        val puntosOrganicos = transacciones
            .filter { it.categoria == "Orgánicos" }
            .sumOf { it.puntosPorCompra }
        return puntosOrganicos >= 1000
    }

    fun validarEspecialistaEnTextiles(transacciones: List<ProductoReciclable>): Boolean {
        val puntosTextiles = transacciones
            .filter { it.categoria == "Textiles" }
            .sumOf { it.puntosPorCompra }
        return puntosTextiles >= 1000
    }

    fun validarEspecialistaEnElectronicos(transacciones: List<ProductoReciclable>): Boolean {
        val puntosElectronicos = transacciones
            .filter { it.categoria == "Electrónicos" }
            .sumOf { it.puntosPorCompra }
        return puntosElectronicos >= 1000
    }

    fun validarEspecialistaEnMadera(transacciones: List<ProductoReciclable>): Boolean {
        val puntosMadera = transacciones
            .filter { it.categoria == "Madera" }
            .sumOf { it.puntosPorCompra }
        return puntosMadera >= 1000
    }

    fun validarEspecialistaEnOtros(transacciones: List<ProductoReciclable>): Boolean {
        val puntosOtros = transacciones
            .filter { it.categoria == "Otros" }
            .sumOf { it.puntosPorCompra }
        return puntosOtros >= 1000
    }
    fun validarHeroeDelReciclaje(co2Evitado: Double): Boolean {
        return co2Evitado >= 100.0
    }

    fun validarGuardianDelPlaneta(residuosReducidosEnUnidades: Double): Boolean {
        return residuosReducidosEnUnidades >= 1000.0 // 1 tonelada = 1000 kg
    }
    fun validarCompartirEsCuidar(compartidosEnRedes: Int): Boolean {
        return compartidosEnRedes >= 1
    }

    fun validarInfluencerVerde(compartidosEnRedes: Int): Boolean {
        return compartidosEnRedes >= 10
    }

    fun validarComunidadActiva(interacciones: Int): Boolean {
        return interacciones >= 10
    }

    fun validarRecicladorDeVerano(transacciones: List<ProductoReciclable>): Boolean {
        Log.d("ValidarRecicladorDeVerano", "Transacciones: $transacciones")
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val transaccionesVerano = transacciones.filter {
            val date = dateFormat.parse(it.fechaPublicacion)
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            val mes = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is zero-based
            mes in 6..8
        }
        return transaccionesVerano.size >= 20
    }

    fun validarRecicladorDeInvierno(transacciones: List<ProductoReciclable>): Boolean {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

        val transaccionesInvierno = transacciones.filter {
            val date = dateFormat.parse(it.fechaPublicacion)
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            val mes = calendar.get(Calendar.MONTH) + 1
            mes == 12 || mes == 1 || mes == 2 // Invierno: diciembre, enero, febrero
        }
        return transaccionesInvierno.size >= 20
    }

    fun validarEquipoVerde(transaccionesEnGrupo: Int): Boolean {
        return transaccionesEnGrupo >= 1
    }

    fun validarReciclajeComunitario(eventosParticipados: Int): Boolean {
        return eventosParticipados >= 1
    }
    fun actualizarLogrosUsuario(usuario: Usuario, transacciones: List<ProductoReciclable>, puntosTotales: Int, co2Evitado: Double, residuosReducidosEnUnidades: Double, compartidosEnRedes: Int, interacciones: Int, transaccionesEnGrupo: Int, eventosParticipados: Int): Usuario {
        val logrosCompletados = mutableListOf<String>()

        //Mostrar el ultimo numero al principio de la pantalla de logros
        if (validarRecicladorNovato(transacciones)) logrosCompletados.add("1")
        if (validarRecicladorComprometido(transacciones)) logrosCompletados.add("2")
        if (validarRecicladorExperto(transacciones)) logrosCompletados.add("3")
        if (validarRecicladorMaster(transacciones)) logrosCompletados.add("4")
        if (validarRecordPersonal(transacciones)) logrosCompletados.add("5")
        if (validarPuntosDorados(puntosTotales)) logrosCompletados.add("6")
        if (validarPuntosDePlatino(puntosTotales)) logrosCompletados.add("7")
        if (validarPuntosLegendarios(puntosTotales)) logrosCompletados.add("8")
        if (validarAmigoDelMedioAmbiente(transacciones)) logrosCompletados.add("9")
        if (validarEspecialistaEnPlasticos(transacciones)) logrosCompletados.add("10")
        if (validarEspecialistaEnMetales(transacciones)) logrosCompletados.add("11")
        if (validarEspecialistaEnPapelYCarton(transacciones)) logrosCompletados.add("12")
        if (validarEspecialistaEnVidrio(transacciones)) logrosCompletados.add("13")
        if (validarEspecialistaEnOrganicos(transacciones)) logrosCompletados.add("14")
        if (validarEspecialistaEnTextiles(transacciones)) logrosCompletados.add("15")
        if (validarEspecialistaEnElectronicos(transacciones)) logrosCompletados.add("16")
        if (validarEspecialistaEnMadera(transacciones)) logrosCompletados.add("17")
        if (validarEspecialistaEnOtros(transacciones)) logrosCompletados.add("18")
        if (validarHeroeDelReciclaje(co2Evitado)) logrosCompletados.add("19")
        if (validarGuardianDelPlaneta(residuosReducidosEnUnidades)) logrosCompletados.add("20")
        if (validarCompartirEsCuidar(compartidosEnRedes)) logrosCompletados.add("21")
        if (validarInfluencerVerde(compartidosEnRedes)) logrosCompletados.add("22") // Cuando el usuario de compartir
        if (validarComunidadActiva(interacciones)) logrosCompletados.add("23") // Cuando el usuario de comentar
        if (validarRecicladorDeVerano(transacciones)) logrosCompletados.add("24")
        if (validarRecicladorDeInvierno(transacciones)) logrosCompletados.add("25")
        if (validarEquipoVerde(transaccionesEnGrupo)) logrosCompletados.add("26")
        if (validarReciclajeComunitario(eventosParticipados)) logrosCompletados.add("27")

        return usuario.copy(logrosPorId = logrosCompletados.joinToString(","))
    }
}