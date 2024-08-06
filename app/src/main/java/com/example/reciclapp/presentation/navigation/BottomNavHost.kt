package com.example.reciclapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciclapp.presentation.ui.menu.ui.ContactListScreen
import com.example.reciclapp.presentation.ui.menu.ui.Contenidos
import com.example.reciclapp.presentation.ui.menu.ui.Inicio
import com.example.reciclapp.presentation.ui.menu.ui.ItemsMenu
import com.example.reciclapp.presentation.ui.menu.ui.Perfil
import com.example.reciclapp.presentation.ui.menu.ui.Premium
import com.example.reciclapp.presentation.viewmodel.UserViewModel

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
            Contenidos()
        }
        composable(ItemsMenu.Pantalla3.ruta) {
            Inicio()
        }
        composable(ItemsMenu.Pantalla4.ruta) {
            ContactListScreen(navController)
        }
        composable(ItemsMenu.Pantalla5.ruta) {
            Premium()
        }
    }
}
