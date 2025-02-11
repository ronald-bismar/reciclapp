package com.example.reciclapp.presentation.ui.menu.ui.content.statistics

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Park
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Composable para mostrar la pantalla de estadísticas detalladas
@Composable
fun DetailedStatisticsScreen() {
    val modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top // Alinea el contenido a la parte superior
    ) {
        BadgeNuevoLogro(modifier = Modifier.fillMaxWidth()) // Asegura que el Row ocupe todo el ancho
        CardNivelReciclaje()
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
            containerColor = Color.White // Fondo blanco
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
            .padding(start = 0.dp, end = 0.dp, top = 16.dp, bottom = 2.dp) // Padding diferente arriba y abajo
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
            .width(30.dp) // Hace que sea más ancho
            .height(32.dp) // Mantiene la altura original
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp) // Bordes redondeados para formar un óvalo
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Park,
            contentDescription = "Ícono de arbol",
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )
    }
}


@Composable
fun BadgeNuevoLogro(modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.padding(vertical = 4.dp)
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

// Función de vista previa para la pantalla de estadísticas detalladas
@Preview(showBackground = true)
@Composable
fun PreviewDetailedStatisticsScreen() {
    DetailedStatisticsScreen()
}