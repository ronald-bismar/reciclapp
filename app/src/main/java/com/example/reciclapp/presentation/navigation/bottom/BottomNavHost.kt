package com.example.reciclapp.presentation.navigation.bottom

import HistorialComprasScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.ui.menu.ui.ContactListScreen
import com.example.reciclapp.util.ItemsMenu
import com.example.reciclapp.presentation.ui.menu.ui.RankingCompradoresScreen
import com.example.reciclapp.presentation.ui.menu.ui.SocialMediaScreenVendedores
import com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips.GlobalWasteApi
import com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips.NewsTipsScreen
import com.example.reciclapp.presentation.ui.menu.ui.content.statistics.DetailedStatisticsScreen
import com.example.reciclapp.presentation.ui.menu.ui.vistas.mapa.MapsView
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavHost(
    mainNavController: NavController,
    navHostController: NavHostController,
    userViewModel: UserViewModel,
    ubicacionViewModel: UbicacionViewModel,
    vendedoresViewModel: VendedoresViewModel,
    compradoresViewModel: CompradoresViewModel,
    startDestination: String
) {
    val globalWasteApi = remember { GlobalWasteApi.create() }
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(ItemsMenu.PantallaV1.ruta) {
            NewsTipsScreen(api = globalWasteApi)
        }
        composable(ItemsMenu.PantallaV2.ruta) {
            DetailedStatisticsScreen(userViewModel)
        }
        composable(ItemsMenu.PantallaV3.ruta) {
            MapsView(mainNavController, ubicacionViewModel)
        }
        composable(ItemsMenu.PantallaV4.ruta) {
            ContactListScreen(mainNavController, userViewModel.user.value?: Usuario(), userViewModel)
        }
        composable(ItemsMenu.PantallaV5.ruta) {
            SocialMediaScreenVendedores(vendedoresViewModel, mainNavController)
        }
        composable(ItemsMenu.PantallaC1.ruta) {
            RankingCompradoresScreen(compradoresViewModel)
        }
        composable(ItemsMenu.PantallaC2.ruta) {
            DetailedStatisticsScreen(userViewModel)
        }
        composable(ItemsMenu.PantallaC3.ruta) {
            SocialMediaScreenVendedores(vendedoresViewModel, mainNavController)
        }
        composable(ItemsMenu.PantallaC4.ruta) {
            ContactListScreen(mainNavController, userViewModel.user.value?: Usuario(), userViewModel)
        }
        composable(ItemsMenu.PantallaC5.ruta) {
            HistorialComprasScreen(compradoresViewModel)
        }
    }
}
