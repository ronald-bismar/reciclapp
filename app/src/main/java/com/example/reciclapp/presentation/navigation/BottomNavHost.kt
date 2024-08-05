package com.example.reciclapp.presentation.navigation

import com.example.reciclapp.presentation.ui.menu.ui.MapsView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciclapp.presentation.ui.menu.ui.ContactListScreen
import com.example.reciclapp.presentation.ui.menu.ui.Inicio
import com.example.reciclapp.presentation.ui.menu.ui.Items_menu
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
        startDestination = Items_menu.Pantalla3.ruta
    ) {


        composable(Items_menu.Pantalla1.ruta) {
            // Text(text = "Pantalla 1")
            Perfil(userViewModel)

        }
        composable(Items_menu.Pantalla2.ruta) {
            //Text(text = "Pantalla 2")
            userViewModel.user.observeAsState().value?.idUsuario?.let { it1 ->
                MapsView(idUsuario = it1)
            }

        }
        composable(Items_menu.Pantalla3.ruta) {
            Text(text = "Pantalla 3")
            Inicio()

        }
        composable(Items_menu.Pantalla4.ruta) {
            Text(text = "Pantalla 4")
            ContactListScreen(navController)
        }
        composable(Items_menu.Pantalla5.ruta) {
            Text(text = "Pantalla 5")
            Premium()
        }
    }
}
