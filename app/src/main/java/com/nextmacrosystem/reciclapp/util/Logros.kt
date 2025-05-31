package com.nextmacrosystem.reciclapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Tab
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WbSunny
import com.nextmacrosystem.reciclapp.domain.entities.Logro

object Logros {
    // Logros Basados en Cantidad y Frecuencia
    val RecicladorNovato = Logro(
        idLogro = "1",
        titulo = "Reciclador Novato",
        descripcion = "Realiza tu primera transacción de reciclaje.",
        badge = Icons.Default.Star,
        mensajeFelicitacion = "¡Felicidades por tu primera transacción de reciclaje!"
    )

    val RecicladorComprometido = Logro(
        idLogro = "2",
        titulo = "Reciclador Comprometido",
        descripcion = "Realiza 10 transacciones de reciclaje.",
        badge = Icons.Default.ThumbUp,
        mensajeFelicitacion = "¡Buen trabajo! Has completado 10 transacciones."
    )

    val RecicladorExperto = Logro(
        idLogro = "3",
        titulo = "Reciclador Experto",
        descripcion = "Realiza 50 transacciones de reciclaje.",
        badge = Icons.Default.Grade,
        mensajeFelicitacion = "¡Eres un experto! Has completado 50 transacciones."
    )

    val RecicladorMaster = Logro(
        idLogro = "4",
        titulo = "Reciclador Máster",
        descripcion = "Realiza 100 transacciones de reciclaje.",
        badge = Icons.Default.Flag,
        mensajeFelicitacion = "¡Eres un máster del reciclaje! Has completado 100 transacciones."
    )

    val RecordPersonal = Logro(
        idLogro = "5",
        titulo = "Récord Personal",
        descripcion = "Realiza la mayor cantidad de reciclaje en un solo día.",
        badge = Icons.Default.TrendingUp,
        mensajeFelicitacion = "¡Nuevo récord! Has reciclado más que nunca en un solo día."
    )

    // Logros Basados en Puntos
    val PuntosDorados = Logro(
        idLogro = "6",
        titulo = "Puntos Dorados",
        descripcion = "Acumula 1000 puntos en total.",
        badge = Icons.Default.MonetizationOn,
        mensajeFelicitacion = "¡Felicidades! Has alcanzado los 1000 puntos."
    )

    val PuntosDePlatino = Logro(
        idLogro = "7",
        titulo = "Puntos de Platino",
        descripcion = "Acumula 5000 puntos en total.",
        badge = Icons.Default.MonetizationOn,
        mensajeFelicitacion = "¡Increíble! Has alcanzado los 5000 puntos."
    )

    val PuntosLegendarios = Logro(
        idLogro = "8",
        titulo = "Puntos Legendarios",
        descripcion = "Acumula 10000 puntos en total.",
        badge = Icons.Default.MonetizationOn,
        mensajeFelicitacion = "¡Leyenda! Has alcanzado los 10000 puntos."
    )

    // Logros Específicos por Categoría
    val AmigoDelMedioAmbiente = Logro(
        idLogro = "9",
        titulo = "Amigo del Medio Ambiente",
        descripcion = "Realiza al menos una transacción en cada categoría de reciclaje.",
        badge = Icons.Default.Nature,
        mensajeFelicitacion = "¡Eres un amigo del planeta! Has reciclado en todas las categorías."
    )

    val EspecialistaEnPlasticos = Logro(
        idLogro = "10",
        titulo = "Especialista en Plásticos",
        descripcion = "Acumula 1000 puntos en la categoría de plásticos.",
        badge = Icons.Default.Recycling,
        mensajeFelicitacion = "¡Eres un experto en plásticos! Has superado los 1000 puntos."
    )

    val EspecialistaEnMetales = Logro(
        idLogro = "11",
        titulo = "Especialista en Metales",
        descripcion = "Acumula 1000 puntos en la categoría de metales.",
        badge = Icons.Default.Build,
        mensajeFelicitacion = "¡Eres un experto en metales! Has superado los 1000 puntos."
    )

    val EspecialistaEnPapelYCarton = Logro(
        idLogro = "12",
        titulo = "Especialista en Papel y Cartón",
        descripcion = "Acumula 1000 puntos en la categoría de papel y cartón.",
        badge = Icons.Default.Description,
        mensajeFelicitacion = "¡Eres un experto en papel y cartón! Has superado los 1000 puntos."
    )

    val EspecialistaEnVidrio = Logro(
        idLogro = "13",
        titulo = "Especialista en Vidrio",
        descripcion = "Acumula 1000 puntos en la categoría de vidrio.",
        badge = Icons.Default.LocalDrink,
        mensajeFelicitacion = "¡Eres un experto en vidrio! Has superado los 1000 puntos."
    )

    val EspecialistaEnOrganicos = Logro(
        idLogro = "14",
        titulo = "Especialista en Orgánicos",
        descripcion = "Acumula 1000 puntos en la categoría de orgánicos.",
        badge = Icons.Default.Spa,
        mensajeFelicitacion = "¡Eres un experto en orgánicos! Has superado los 1000 puntos."
    )

    val EspecialistaEnTextiles = Logro(
        idLogro = "15",
        titulo = "Especialista en Textiles",
        descripcion = "Acumula 1000 puntos en la categoría de textiles.",
        badge = Icons.Default.Style,
        mensajeFelicitacion = "¡Eres un experto en textiles! Has superado los 1000 puntos."
    )

    val EspecialistaEnElectronicos = Logro(
        idLogro = "16",
        titulo = "Especialista en Electrónicos",
        descripcion = "Acumula 1000 puntos en la categoría de electrónicos.",
        badge = Icons.Default.Devices,
        mensajeFelicitacion = "¡Eres un experto en electrónicos! Has superado los 1000 puntos."
    )

    val EspecialistaEnMadera = Logro(
        idLogro = "17",
        titulo = "Especialista en Madera",
        descripcion = "Acumula 1000 puntos en la categoría de madera.",
        badge = Icons.Default.Tab,
        mensajeFelicitacion = "¡Eres un experto en madera! Has superado los 1000 puntos."
    )

    val EspecialistaEnOtros = Logro(
        idLogro = "18",
        titulo = "Especialista en Otros",
        descripcion = "Acumula 1000 puntos en la categoría de otros.",
        badge = Icons.Default.MoreHoriz,
        mensajeFelicitacion = "¡Eres un experto en otros materiales! Has superado los 1000 puntos."
    )

    // Logros de Impacto Ambiental
    val HeroeDelReciclaje = Logro(
        idLogro = "19",
        titulo = "Héroe del Reciclaje",
        descripcion = "Evita la emisión de 100 kg de CO2 a través del reciclaje.",
        badge = Icons.Default.Eco,
        mensajeFelicitacion = "¡Eres un héroe del reciclaje! Has evitado 100 kg de CO2."
    )

    val GuardianDelPlaneta = Logro(
        idLogro = "20",
        titulo = "Guardian del Planeta",
        descripcion = "Contribuye a la reducción de 1 tonelada de residuos.",
        badge = Icons.Default.Public,
        mensajeFelicitacion = "¡Eres un guardián del planeta! Has reducido 1 tonelada de residuos."
    )

    // Logros Sociales
    val CompartirEsCuidar = Logro(
        idLogro = "21",
        titulo = "Compartir es Cuidar",
        descripcion = "Comparte tu actividad de reciclaje en redes sociales por primera vez.",
        badge = Icons.Default.Share,
        mensajeFelicitacion = "¡Gracias por compartir! Has inspirado a otros a reciclar."
    )

    val InfluencerVerde = Logro(
        idLogro = "22",
        titulo = "Influencer Verde",
        descripcion = "Comparte tu actividad de reciclaje en redes sociales 10 veces.",
        badge = Icons.Default.ThumbUp,
        mensajeFelicitacion = "¡Eres un influencer verde! Has compartido 10 veces."
    )

    val ComunidadActiva = Logro(
        idLogro = "23",
        titulo = "Comunidad Activa",
        descripcion = "Comenta y da like en 10 publicaciones de otros usuarios.",
        badge = Icons.Default.Forum,
        mensajeFelicitacion = "¡Eres parte activa de la comunidad! Has interactuado 10 veces."
    )

    // Logros de Temporada
    val RecicladorDeVerano = Logro(
        idLogro = "24",
        titulo = "Reciclador de Verano",
        descripcion = "Realiza 20 transacciones durante los meses de verano.",
        badge = Icons.Default.WbSunny,
        mensajeFelicitacion = "¡Eres un reciclador de verano! Has completado 20 transacciones."
    )

    val RecicladorDeInvierno = Logro(
        idLogro = "25",
        titulo = "Reciclador de Invierno",
        descripcion = "Realiza 20 transacciones durante los meses de invierno.",
        badge = Icons.Default.AcUnit,
        mensajeFelicitacion = "¡Eres un reciclador de invierno! Has completado 20 transacciones."
    )

    // Logros de Colaboración
    val EquipoVerde = Logro(
        idLogro = "26",
        titulo = "Equipo Verde",
        descripcion = "Realiza transacciones en grupo con amigos o familiares.",
        badge = Icons.Default.Group,
        mensajeFelicitacion = "¡Trabajo en equipo! Has reciclado con amigos o familiares."
    )

    val ReciclajeComunitario = Logro(
        idLogro = "27",
        titulo = "Reciclaje Comunitario",
        descripcion = "Participa en un evento de reciclaje comunitario organizado a través de la app.",
        badge = Icons.Default.Event,
        mensajeFelicitacion = "¡Gracias por participar! Has contribuido a un evento comunitario."
    )

    // Lista de todos los logros
    val listaDeLogros = listOf(
        RecicladorNovato,
        RecicladorComprometido,
        RecicladorExperto,
        RecicladorMaster,
        RecordPersonal,
        PuntosDorados,
        PuntosDePlatino,
        PuntosLegendarios,
        AmigoDelMedioAmbiente,
        EspecialistaEnPlasticos,
        EspecialistaEnMetales,
        EspecialistaEnPapelYCarton,
        EspecialistaEnVidrio,
        EspecialistaEnOrganicos,
        EspecialistaEnTextiles,
        EspecialistaEnElectronicos,
        EspecialistaEnMadera,
        EspecialistaEnOtros,
        HeroeDelReciclaje,
        GuardianDelPlaneta,
        CompartirEsCuidar,
        InfluencerVerde,
        ComunidadActiva,
        RecicladorDeVerano,
        RecicladorDeInvierno,
        EquipoVerde,
        ReciclajeComunitario
    )
}