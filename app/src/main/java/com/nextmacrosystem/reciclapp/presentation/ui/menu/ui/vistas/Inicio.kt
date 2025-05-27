package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Inicio() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Inicio",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(menuItems) { item ->
                MenuItemCard(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

data class MenuItem(
    val title: String,
    val description: String,
    val details: String? = null, // Nuevo campo para detalles
    val icon: @Composable () -> Unit
)

val menuItems = listOf(
    MenuItem(
        "Tareas y Recompensas",
        "Completa tareas y gana recompensas",
        details = "Aquí puedes encontrar una lista de tareas que puedes completar para ganar recompensas. Las recompensas puedenincluir puntos, descuentos y más.",
        icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Tasks") }
    ),
    MenuItem(
        "Calendario de Eventos",
        "Consulta eventos y actividades de reciclaje",
        details = "Mantente al día con los eventos y actividades de reciclaje en tu área. Encuentra oportunidades para participar y aprender más sobre la sostenibilidad.",
        icon = { Icon(Icons.Filled.DateRange, contentDescription = "Calendar") } // Cambiado a Event
    ),
    MenuItem(
        "Contribuciones de la Comunidad",
        "Comparte y colabora con otros en la comunidad",
        details = "Conéctate con otros miembros de la comunidad de reciclaje. Comparte tus ideas, consejos y experiencias para promover un estilo de vida más sostenible.",
        icon = { Icon(Icons.Filled.Face, contentDescription = "Community") } // Cambiado a People
    ),
    MenuItem(
        "Centro de Ayuda y Soporte",
        "Obtén ayuda y soporte técnico",
        details = "Si tienes alguna pregunta o necesitas ayuda con la aplicación, visita nuestro centro de ayuda y soporte. Nuestro equipo estará encantado de ayudarte.",
        icon = { Icon(Icons.Filled.Info, contentDescription = "Help") } // Cambiado a Help
    ),
    MenuItem(
        "Promociones y Ofertas",
        "Aprovecha promociones y ofertas especiales",
        details = "Descubre promociones y ofertas especiales en productos y servicios relacionados con el reciclaje. Ahorra dinero mientras contribuyes al medio ambiente.",
        icon = { Icon(Icons.Filled.Star, contentDescription = "Offers") } // Cambiado a LocalOffer
    ),
    MenuItem(
        "Configuración y Preferencias","Ajusta tus configuraciones y preferencias",
        details = "Personaliza tu experiencia en la aplicación ajustando tus configuraciones y preferencias. Puedes cambiar tu idioma, notificaciones y más.",
        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
    )
)

@Composable
fun MenuItemCard(item: MenuItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle item click */ }
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(containerColor= MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    item.icon()
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
            // Mostrar detalles si están disponibles
            item.details?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}