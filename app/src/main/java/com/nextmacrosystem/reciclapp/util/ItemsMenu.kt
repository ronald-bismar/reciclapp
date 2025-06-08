<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/util/ItemsMenu.kt
package com.example.reciclapp_bolivia.util
========
package com.nextmacrosystem.reciclapp.util
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/util/ItemsMenu.kt

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenu(
    val icon: ImageVector,
    val title: String,
    val ruta: String
) {
    abstract val route: String

    // Vendedores

    data object PantallaV1 : ItemsMenu(Icons.Default.Person, "Noticias", "Noticias") {
        override val route: String = "pantallaV1"
    }

    data object PantallaV2 : ItemsMenu(Icons.Default.List, "Mis puntos", "Mis puntos") {
        override val route: String = "pantallaV2"
    }

    data object PantallaV3 : ItemsMenu(Icons.Default.LocationOn, "Mapa", "Mapa") {
        override val route: String = "pantallaV3"
    }

    data object PantallaV4 : ItemsMenu(Icons.Default.Call, "Comprador", "Comprador") {
        override val route: String = "pantallaV4"
    }

    data object PantallaV5 : ItemsMenu(Icons.Default.Star, "Posts", "Posts") {
        override val route: String = "pantallaV5"
    }

    //Compradores

    data object PantallaC1 : ItemsMenu(Icons.Default.Leaderboard, "Ranking", "RankingCompradores") {
        override val route: String = "pantallaC1"
    }

    data object PantallaC2 : ItemsMenu(Icons.Default.List, "Mis puntos", "Mis puntos") {
        override val route: String = "pantallaC2"
    }

    data object PantallaC3 : ItemsMenu(Icons.Default.Star, "Posts", "Posts") {
        override val route: String = "pantallaC3"
    }

    data object PantallaC4 : ItemsMenu(Icons.Default.Call, "Vendedor", "Vendedor") {
        override val route: String = "pantallaC4"
    }

    data object PantallaC5 : ItemsMenu(Icons.Default.ShoppingCart, "Compras", "HistorialCompras") {
        override val route: String = "pantallaC5"
    }
}