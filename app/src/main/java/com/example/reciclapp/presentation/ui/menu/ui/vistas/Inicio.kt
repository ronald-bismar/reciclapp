package com.example.reciclapp.presentation.ui.menu.ui.vistas

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

data class MenuItem(val title: String, val description: String, val icon: @Composable () -> Unit)

val menuItems = listOf(
    MenuItem(
        "Resumen del Usuario", "Ver tu perfil y estadísticas de reciclaje",
        icon = { Icon(Icons.Filled.Person, contentDescription = "User") }
    ),
    MenuItem(
        "Puntos de Reciclaje Cercanos", "Encuentra los puntos de reciclaje más cercanos",
        icon = { Icon(Icons.Filled.Place, contentDescription = "Place") }
    ),
    MenuItem(
        "Noticias y Consejos de Reciclaje", "Lee las últimas noticias y consejos sobre reciclaje",
        icon = { Icon(Icons.Filled.Info, contentDescription = "News") }
    ),
    MenuItem(
        "Estadísticas de Reciclaje", "Visualiza tus estadísticas de reciclaje",
        icon = { Icon(Icons.Filled.Email, contentDescription = "Statistics") }
    ),
    MenuItem(
        "Tareas y Recompensas", "Completa tareas y gana recompensas",
        icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Tasks") }
    ),
    MenuItem(
        "Calendario de Eventos", "Consulta eventos y actividades de reciclaje",
        icon = { Icon(Icons.Filled.Email, contentDescription = "Calendar") }
    ),
    MenuItem(
        "Contribuciones de la Comunidad", "Comparte y colabora con otros en la comunidad",
        icon = { Icon(Icons.Filled.Email, contentDescription = "Community") }
    ),
    MenuItem(
        "Centro de Ayuda y Soporte", "Obtén ayuda y soporte técnico",
        icon = { Icon(Icons.Filled.Email, contentDescription = "Help") }
    ),
    MenuItem(
        "Promociones y Ofertas", "Aprovecha promociones y ofertas especiales",
        icon = { Icon(Icons.Filled.Email, contentDescription = "Offers") }
    ),
    MenuItem(
        "Configuración y Preferencias", "Ajusta tus configuraciones y preferencias",
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
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
    }
}
