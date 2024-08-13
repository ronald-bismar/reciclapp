package com.example.reciclapp.presentation.navigation.bottom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciclapp.presentation.ui.menu.ui.ContactListScreen
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Inicio
import com.example.reciclapp.presentation.ui.menu.ui.ItemsMenu
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Perfil
import com.example.reciclapp.presentation.ui.menu.ui.content.statistics.DetailedStatisticsScreen
import com.example.reciclapp.presentation.ui.ayuda.ui.SimpleAyudaScreen
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavHost(
    navController: NavController,
    navHostController: NavHostController,
    userViewModel: UserViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = ItemsMenu.Pantalla3.ruta
    ) {
        composable(ItemsMenu.Pantalla1.ruta) {
            Perfil(userViewModel)
        }
        composable(ItemsMenu.Pantalla2.ruta) {
            DetailedStatisticsScreen()
        }
        composable(ItemsMenu.Pantalla3.ruta) {
            Inicio()
        }
        composable(ItemsMenu.Pantalla4.ruta) {
            ContactListScreen(navController)
        }
        composable(ItemsMenu.Pantalla5.ruta) {
            SimpleAyudaScreen()
        }
    }
}
