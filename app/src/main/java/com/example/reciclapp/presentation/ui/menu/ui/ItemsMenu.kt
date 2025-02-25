package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenu(
    val icon: ImageVector,
    val title: String,
    val ruta: String
) {
    abstract val route: String

    data object Pantalla1 : ItemsMenu(Icons.Default.Person, "Noticias", "Perfil") {
        override val route: String = "pantalla1"
    }

    data object Pantalla2 : ItemsMenu(Icons.Default.List, "Mis puntos", "Contenidos") {
        override val route: String = "pantalla2"
    }

    data object Pantalla3 : ItemsMenu(Icons.Default.LocationOn, "Mapa", "map") {
        override val route: String = "pantalla3"
    }

    data object Pantalla4 : ItemsMenu(Icons.Default.Call, "Comprador", "Contacto") {
        override val route: String = "pantalla4"
    }

    data object Pantalla5 : ItemsMenu(Icons.Default.Star, "Posts", "socialmediascreen") {
        override val route: String = "pantalla5"
    }

    data object Pantalla4Vendedores : ItemsMenu(Icons.Default.Call, "Vendedor", "Contacto") {
        override val route: String = "pantalla4"
    }
}
