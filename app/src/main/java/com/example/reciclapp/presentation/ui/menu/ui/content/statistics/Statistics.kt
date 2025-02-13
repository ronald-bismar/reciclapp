package com.example.reciclapp.presentation.ui.menu.ui.content.statistics

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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reciclapp.R

// Composable para mostrar la pantalla de estadísticas detalladas
@Composable
fun DetailedStatisticsScreen() {
    val modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { BadgeNuevoLogro(modifier = Modifier.fillMaxWidth()) }
        item { CardNivelReciclaje() }
        item { RachaReciclaje() }
        item { ImpactoAmbiental() }
        item { LogrosDesbloqueados() }
        item { EstadisticasCategorias() }
    }
}


@Composable
fun CardNivelReciclaje() {
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
            DatosEcoGuerrero()
            BarraNivel()
        }
    }
}


@Composable
fun BarraNivel() {
    Column(Modifier.fillMaxWidth()) {
        NivelAnteriorYSiguienteNivel()
        BarraNivelDeReciclaje(0.6F)
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
private fun NivelAnteriorYSiguienteNivel() {
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
            text = "Nivel 5",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Light
        )
        Text(
            text = "Nivel 6",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal
        )
    }
}


@Composable
fun DatosEcoGuerrero() {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NombreEcoGuerrero()
        PuntosEcoGuerrero()
    }
}

@Composable
fun PuntosEcoGuerrero() {
    Row(Modifier.padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        IconoPuntos()
        Puntos()
    }
}

@Composable
fun Puntos() {
    Text(
        text = "1250", style = MaterialTheme.typography.bodyMedium,
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
fun NombreEcoGuerrero() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconoArbolito()
        Spacer(modifier = Modifier.width(10.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "EcoGuerrero", style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Nivel 5",
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
fun BadgeNuevoLogro(modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .padding(vertical = 4.dp)
            .wrapContentHeight() // Ajusta la altura al contenido
            .background(Color(0xFFFFF1A8))
            .border(0.4.dp, color = Color(0xFFF39A00), shape = RoundedCornerShape(8.dp))
    ) {
        IconoBadge()
        TextoBadge()
    }
}

@Composable
fun TextoBadge() {
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
            text = "Felicidades has reciclado 7 dias seguidos!",
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun IconoBadge() {
    Icon(
        imageVector = Icons.Outlined.EmojiEvents,
        contentDescription = "Ícono de trofeo",
        Modifier
            .padding(10.dp)
            .width(20.dp)
            .height(20.dp),
    )
}

@Composable
fun RachaReciclaje() {
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
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(7) { index ->
                    DiaRacha(activo = index < 7)
                }
            }

            Text(
                text = "¡7 días seguidos reciclando!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DiaRacha(activo: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (activo) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(32.dp)
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
fun ImpactoAmbiental() {
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
                repeat(3) { index ->
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

            Text(
                text = "¡Has salvado el equivalente a 3 árboles! 🌳",
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
        }
    }
}

@Composable
fun LogrosDesbloqueados() {
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

            val logros = listOf(
                "¡Primer Reciclaje!",
                "Maestro del Plástico",
                "Defensor del Planeta"
            )

            logros.forEach { logro ->
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
                    Text(text = logro)
                }
            }
        }
    }
}

@Composable
fun EstadisticasCategorias() {
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

            val categorias = listOf(
                Triple("Plástico", 450, MaterialTheme.colorScheme.primary),
                Triple("Papel", 320, MaterialTheme.colorScheme.secondary),
                Triple("Vidrio", 280, MaterialTheme.colorScheme.tertiary),
                Triple("Metal", 200, Color(0xFFF39A00))
            )

            categorias.forEach { (categoria, puntos, color) ->
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
                        progress = puntos / 450f,
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

// Función de vista previa para la pantalla de estadísticas detalladas
@Preview(showBackground = true)
@Composable
fun PreviewDetailedStatisticsScreen() {
    DetailedStatisticsScreen()
}