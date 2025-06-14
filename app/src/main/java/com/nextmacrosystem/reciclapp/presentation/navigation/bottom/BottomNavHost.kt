package com.nextmacrosystem.reciclapp.presentation.navigation.bottom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.BuyersScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.HistorialComprasScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.RankingCompradoresScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.SellersScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.SocialMediaScreenVendedores
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.newsandtips.GlobalWasteApi
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.newsandtips.NewsTipsScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.statistics.DetailedStatisticsScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.mapa.MapsView
import com.nextmacrosystem.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UserViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.VendedoresViewModel
import com.nextmacrosystem.reciclapp.util.ItemsMenu

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
            BuyersScreen(mainNavController, userViewModel, ubicacionViewModel)
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
            SellersScreen(mainNavController, userViewModel, ubicacionViewModel)
        }
        composable(ItemsMenu.PantallaC5.ruta) {
            HistorialComprasScreen(compradoresViewModel)
        }
    }
}
