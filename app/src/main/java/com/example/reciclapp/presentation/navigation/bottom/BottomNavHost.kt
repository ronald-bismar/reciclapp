package com.example.reciclapp.presentation.navigation.bottom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciclapp.presentation.ui.menu.ui.ContactListScreen
import com.example.reciclapp.presentation.ui.menu.ui.ItemsMenu
import com.example.reciclapp.presentation.ui.menu.ui.SocialMediaScreenVendedores
import com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips.GlobalWasteApi
import com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips.NewsTipsScreen
import com.example.reciclapp.presentation.ui.menu.ui.content.statistics.DetailedStatisticsScreen
import com.example.reciclapp.presentation.ui.menu.ui.vistas.mapa.MapsView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavHost(
    mainNavController: NavController,
    navHostController: NavHostController,
    idUsuario: Int

) {
    val globalWasteApi = remember { GlobalWasteApi.create() }
    NavHost(
        navController = navHostController,
        startDestination = ItemsMenu.Pantalla3.ruta
    ) {
        composable(ItemsMenu.Pantalla1.ruta) {
            NewsTipsScreen(api = globalWasteApi)
        }
        composable(ItemsMenu.Pantalla2.ruta) {
            DetailedStatisticsScreen()
        }
        composable(ItemsMenu.Pantalla3.ruta) {
            MapsView(idUsuario, mainNavController)
        }
        composable(ItemsMenu.Pantalla4.ruta) {
            ContactListScreen(mainNavController)
        }
        composable(ItemsMenu.Pantalla5.ruta) {
            SocialMediaScreenVendedores(mainNavController)
        }
    }
}
