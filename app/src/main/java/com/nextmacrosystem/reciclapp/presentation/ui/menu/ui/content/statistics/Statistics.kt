package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.statistics

import RachaReciclaje
import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nextmacrosystem.reciclapp.R
import com.nextmacrosystem.reciclapp.domain.entities.Logro
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UserViewModel
import com.nextmacrosystem.reciclapp.util.ImpactoAmbientalUtil
import com.nextmacrosystem.reciclapp.util.Logros
import com.nextmacrosystem.reciclapp.util.NombreNivelUsuario
import com.nextmacrosystem.reciclapp.util.ProductosReciclables
import com.nextmacrosystem.reciclapp.util.ValidarLogros.actualizarLogrosUsuario

private const val TAG = "PantallaPrincipal"

@Composable
fun DetailedStatisticsScreen(userViewModel: UserViewModel) {
    val modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)

    val usuarioConLogrosActualizados = userViewModel.user.value
    val logrosEncontrados = userViewModel.logrosEncontrados.collectAsState().value
    val porcentajeLogrado = userViewModel.porcentajeLogradoEnNivelActual.collectAsState().value
    val siguienteNivel = userViewModel.siguienteNivel.collectAsState().value
    val nombreYPuntosPorCategoria = userViewModel.nombreYPuntosPorCategoria.collectAsState().value
    val rachaSemanal = userViewModel.rachaSemanal.collectAsState().value
    val rachaMensual = userViewModel.rachaMensual.collectAsState().value
    val cantidadArbolesBeneficiados = userViewModel.cantidadArbolesBeneficiados.collectAsState().value

    LaunchedEffect(usuarioConLogrosActualizados) {
        Log.d(TAG, "DetailedStatisticsScreen: $usuarioConLogrosActualizados")
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (logrosEncontrados.isNotEmpty())
            item {
                BadgeNuevoLogro(
                    ultimoLogro = logrosEncontrados.last(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        item {
            usuarioConLogrosActualizados?.let {
                CardNivelReciclaje(
                    usuarioConLogrosActualizados,
                    porcentajeLogrado,
                    it.nivel,
                    siguienteNivel
                )
            }
        }
        if (rachaSemanal > 0 || rachaMensual > 0)
            item { RachaReciclaje(rachaSemanal, rachaMensual) }
        item { ImpactoAmbiental(cantidadArbolesBeneficiados) }
        item { LogrosDesbloqueados(logrosEncontrados) }
        item { EstadisticasCategorias(nombreYPuntosPorCategoria) }
    }
}


@Composable
fun CardNivelReciclaje(
    usuario: Usuario,
    porcentajeLogrado: Int,
    actualNivel: String,
    siguienteNivel: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Sombra sutil
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DatosEcoGuerrero(usuario)
            BarraNivel(porcentajeLogrado, actualNivel, siguienteNivel)
        }
    }
}


@Composable
fun BarraNivel(porcentajeLogrado: Int, actualNivel: String, siguienteNivel: String) {
    Column(Modifier.fillMaxWidth()) {
        NivelAnteriorYSiguienteNivel(actualNivel, siguienteNivel)
        BarraNivelDeReciclaje(porcentajeLogrado.toFloat())
    }
}

@Composable
fun BarraNivelDeReciclaje(nivel: Float) { // nivel debe estar entre 0f y 1f (0% - 100%)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = nivel)
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondary) // Color del progreso
        )
    }
}


@Composable
private fun NivelAnteriorYSiguienteNivel(actualNivel: String, siguienteNivel: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                start = 0.dp,
                end = 0.dp,
                top = 16.dp,
                bottom = 2.dp
            ) // Padding diferente arriba y abajo
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = actualNivel,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Light
        )
        Text(
            text = siguienteNivel,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal
        )
    }
}


@Composable
fun DatosEcoGuerrero(usuario: Usuario) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NombreEcoGuerrero(usuario.nombreNivel, usuario.nivel)
        PuntosEcoGuerrero(usuario.puntaje)
    }
}

@Composable
fun PuntosEcoGuerrero(puntaje: Int) {
    Row(Modifier.padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        IconoPuntos()
        Puntos(puntaje)
    }
}

@Composable
fun Puntos(puntaje: Int) {
    Text(
        text = puntaje.toString(), style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun IconoPuntos() {
    Icon(
        imageVector = Icons.Outlined.StarOutline,
        contentDescription = "Ícono de trofeo",
        Modifier
            .padding(10.dp)
            .width(20.dp)
            .height(20.dp),
        tint = Color(0xFFF39A00)
    )
}

@Composable
fun NombreEcoGuerrero(nombreNivel: String, nivel: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconoArbolito()
        Spacer(modifier = Modifier.width(10.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = nombreNivel, style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = nivel,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun IconoArbolito() {
    Box(
        modifier = Modifier
            .width(38.dp) // Hace que sea más ancho
            .height(40.dp) // Mantiene la altura original
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp) // Bordes redondeados para formar un óvalo
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_tree),
            contentDescription = "Ícono de árbol",
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )
    }
}


@Composable
fun BadgeNuevoLogro(ultimoLogro: Logro, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .padding(vertical = 4.dp)
            .wrapContentHeight()
            .background(Color(0xFFFFF1A8))
            .border(0.4.dp, color = Color(0xFFF39A00), shape = RoundedCornerShape(8.dp))
    ) {
        IconoBadge(ultimoLogro.badge)
        TextoBadge(ultimoLogro.mensajeFelicitacion)
    }
}

@Composable
fun TextoBadge(mensajeFelicitacionLogro: String) {
    Column(
        Modifier.padding(horizontal = 2.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "¡Nuevo Logro Desbloqueado!",
            modifier = Modifier.padding(horizontal = 2.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = mensajeFelicitacionLogro,
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun IconoBadge(badge: ImageVector) {
    Icon(
        imageVector = badge,
        contentDescription = "Ícono de trofeo",
        Modifier
            .padding(10.dp)
            .width(20.dp)
            .height(20.dp),
    )
}

@Composable
fun RachaReciclaje(rachaSemanal: Int, rachaMensual: Int) {

    val mensajeRacha: String =
        if (rachaSemanal > 0)
            "$rachaSemanal semanas seguidas reciclando!"
        else if (rachaMensual > 0)
            "$rachaMensual meses seguidos reciclando!"
        else ""

    val cantidadIconos =
        if (rachaSemanal > 0) rachaSemanal else if (rachaMensual > 0) rachaMensual else 0
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_like), contentDescription = null,
                    tint = Color(0xFFF39A00)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "¡Racha de Reciclaje!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(cantidadIconos) { index ->
                    DiaRachaConIcon(activo = index < cantidadIconos)
                }
            }

            Text(
                text = mensajeRacha,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DiaRachaConIcon(activo: Boolean) {
    Box(
        modifier = Modifier
            .size(32.dp).padding(horizontal = 2.dp)
            .clip(CircleShape)
            .background(if (activo) Color(0xFFF39A00) else Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_leaf),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ImpactoAmbiental(cantidadArbolesBeneficiados: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tu Impacto en el Planeta",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(cantidadArbolesBeneficiados) { index ->
                    val infiniteTransition = rememberInfiniteTransition(label = "")
                    val translateY by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = -10f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        ), label = ""
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_tree),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(40.dp)
                            .offset(y = translateY.dp)
                    )
                }
            }

            if(cantidadArbolesBeneficiados > 0){
                Text(
                    text = "¡Has beneficiado al equivalente a $cantidadArbolesBeneficiados árboles! 🌳",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall
                )

                LinearProgressIndicator(
                    progress = 0.85f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = Color.LightGray
                )
            }else{
                Text(
                    text = "Aun no tienes estadisticas de impacto ambiental",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun LogrosDesbloqueados(logrosEncontrados: List<Logro>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Logros Desbloqueados",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )


            if (logrosEncontrados.isEmpty())
                Text(
                    text = "No has desbloqueado ningún logro",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall
                )
            else
                logrosEncontrados.forEach { logro ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.EmojiEvents,
                            contentDescription = null,
                            tint = Color(0xFFF39A00),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = logro.titulo)
                    }
                }
        }
    }
}

@Composable
fun EstadisticasCategorias(nombreYPuntosPorCategoria: Map<String, Int>) {

    Log.d(TAG, "EstadisticasCategoriasasd: $nombreYPuntosPorCategoria")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Puntos por Categoría",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Convertir el mapa en una lista de Triple (nombre, puntos, color)
            val categorias = nombreYPuntosPorCategoria.map { (categoria, puntos) ->
                Triple(
                    categoria,
                    puntos,
                    when (categoria) {
                        "Plásticos" -> MaterialTheme.colorScheme.primary
                        "Papel y Cartón" -> MaterialTheme.colorScheme.secondary
                        "Vidrio" -> MaterialTheme.colorScheme.tertiary
                        "Metales" -> Color(0xFFF39A00)
                        "Orgánicos" -> Color(0xFF4CAF50)
                        "Textiles" -> Color(0xFF9C27B0)
                        "Electrónicos" -> Color(0xFF607D8B)
                        "Madera" -> Color(0xFF8D6E63)
                        "Otros" -> Color(0xFF795548)
                        else -> Color.Gray // Color por defecto para categorías desconocidas
                    }
                )
            }

            // Definir las primeras tres categorías que siempre se mostrarán
            val categoriasPrioritarias = listOf("Plásticos", "Papel y Cartón", "Vidrio")

            // Filtrar las categorías: mostrar las prioritarias y las que tengan puntos > 0
            val categoriasFiltradas = categorias.filter { (categoria, puntos, _) ->
                categoria in categoriasPrioritarias || puntos > 0
            }

            // Calcular el máximo de puntos para normalizar el progreso
            val maxPuntos = categoriasFiltradas.maxOfOrNull { it.second }?.toFloat() ?: 1f

            // Mostrar cada categoría dinámicamente
            categoriasFiltradas.forEach { (categoria, puntos, color) ->
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = categoria)
                        Text(text = "$puntos pts", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = puntos / maxPuntos, // Normalizar el progreso
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = color,
                        trackColor = Color.LightGray
                    )
                }
            }
        }
    }
}