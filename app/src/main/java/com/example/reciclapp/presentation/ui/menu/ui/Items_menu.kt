package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Items_menu(
    val icon: ImageVector,
    val title: String,
    val ruta: String
) {
    abstract val route: String

    object Pantalla1 : Items_menu(Icons.Default.Person, "Perfil", "Perfil") {
        override val route: String = "pantalla1"
    }

    object Pantalla2 : Items_menu(Icons.Default.List, "Contenidos", "Contenidos") {
        override val route: String = "pantalla2"
    }

    object Pantalla3 : Items_menu(Icons.Default.Home, "Inicio", "Inicio") {
        override val route: String = "pantalla3"
    }

    object Pantalla4 : Items_menu(Icons.Default.Call, "Contacto", "Contacto") {
        override val route: String = "pantalla4"
    }

    object Pantalla5 : Items_menu(Icons.Default.Star, "Premium", "Premium") {
        override val route: String = "pantalla5"
    }
}
