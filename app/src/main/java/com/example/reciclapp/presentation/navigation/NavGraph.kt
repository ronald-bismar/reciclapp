package com.example.reciclapp.presentation.navigation

import CrearProductoReciclableComprador
import CrearProductoReciclableVendedor
import QRScannerScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.presentation.ui.CompartirScreen.CompartirScreen
import com.example.reciclapp.presentation.ui.aboutus.AboutUsScreen
import com.example.reciclapp.presentation.ui.contactate.ContactateConNosotrosScreen
import com.example.reciclapp.presentation.ui.drawer.ui.CalificanosScreen
import com.example.reciclapp.presentation.ui.drawer.ui.MisionVisionScreen
import com.example.reciclapp.presentation.ui.drawer.ui.SimpleAyudaScreen
import com.example.reciclapp.presentation.ui.login.ui.LoginScreen
import com.example.reciclapp.presentation.ui.login.ui.LoginViewModel
import com.example.reciclapp.presentation.ui.menu.ui.CompradorOfertaScreen
import com.example.reciclapp.presentation.ui.menu.ui.IntroductionScreen
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPresentacion
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPrincipal
import com.example.reciclapp.presentation.ui.menu.ui.PresentacionAppScreen
import com.example.reciclapp.presentation.ui.menu.ui.QRGeneratorScreen
import com.example.reciclapp.presentation.ui.menu.ui.ScreenProductsForSale
import com.example.reciclapp.presentation.ui.menu.ui.TransaccionesPendientesScreen
import com.example.reciclapp.presentation.ui.menu.ui.UserTypeScreen
import com.example.reciclapp.presentation.ui.menu.ui.content.myproducts.MyProductsScreen
import com.example.reciclapp.presentation.ui.menu.ui.content.mypurchases.MyProductsToBuyScreen
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Comprador
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Perfil
import com.example.reciclapp.presentation.ui.menu.ui.vistas.SendingProductsScreen
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Vendedor
import com.example.reciclapp.presentation.ui.registro.ui.RegistroScreen
import com.example.reciclapp.presentation.ui.registro.ui.RegistroViewModel
import com.example.reciclapp.presentation.ui.splash.SplashScreenContent
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import com.example.reciclapp.util.NameRoutes.PANTALLAPRINCIPAL
import com.example.reciclapp.util.NameRoutes.QRSCANNER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    mainNavHostController: NavHostController, getUserPreferencesUseCase: GetUserPreferencesUseCase
) {

    //Declaramos aqui nuestros view models para enviarlos a cada pantalla y asi persistir los datos

    val loginViewModel: LoginViewModel = hiltViewModel()
    val registroViewModel: RegistroViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val vendedoresViewModel: VendedoresViewModel = hiltViewModel()
    val compradoresViewModel: CompradoresViewModel = hiltViewModel()
    val transaccionViewModel: TransaccionViewModel = hiltViewModel()
    val ubicacionViewModel: UbicacionViewModel = hiltViewModel()

    var nextScreen by remember { mutableStateOf("splash") }
    var usuario by remember { mutableStateOf(Usuario()) }

    LaunchedEffect(Unit) {
        val (screen, user) = getNextScreenAndKindUser(getUserPreferencesUseCase)
        nextScreen = screen
        usuario = user
    }

    NavHost(navController = mainNavHostController, startDestination = "splash") {
        composable("splash") {
            SplashScreenContent {
                mainNavHostController.navigate(nextScreen) {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("login") {
            LoginScreen(loginViewModel, mainNavHostController)
        }
        composable("registro") {
            RegistroScreen(registroViewModel, mainNavHostController)
        }
        composable(PANTALLAPRINCIPAL) {
            PantallaPrincipal(
                userViewModel,
                vendedoresViewModel,
                compradoresViewModel,
                ubicacionViewModel,
                mainNavHostController,
            )
        }
        composable("pantalla presentacion") {
            PantallaPresentacion(mainNavHostController)
        }
        composable("presentacion app") {
            PresentacionAppScreen(mainNavHostController)
        }
        composable("introduction screen") {
            IntroductionScreen(mainNavHostController)
        }
        composable("Que es Reciclapp") {
            SimpleAyudaScreen()
        }
        composable("Mision y vision") {
            MisionVisionScreen()
        }
        composable("Sobre Nosotros") {
            AboutUsScreen()
        }
        composable("Compartir") {
            CompartirScreen()
        }
        composable("Contactate con nosotros") {
            ContactateConNosotrosScreen()
        }
        composable("Calificanos") {
            CalificanosScreen()
        }

        composable("perfil") {
            Perfil(userViewModel, vendedoresViewModel, mainNavHostController)
        }
        composable("tipoDeUsuario") {
            UserTypeScreen(userViewModel, mainNavHostController)
        }
        composable(
            route = "compradorPerfil/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            Comprador(mainNavHostController, userId, compradoresViewModel, transaccionViewModel)
        }
        composable(
            route = "VendedorPerfil/{userId}/{productoId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("productoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
            Vendedor(
                mainNavHostController, userId, productoId, vendedoresViewModel
            )
        }

        composable("AñadirProductoReciclable") {
            CrearProductoReciclableVendedor(vendedoresViewModel, mainNavHostController)
        }

        composable("AñadirProductoReciclableComprador") {
            CrearProductoReciclableComprador(compradoresViewModel, mainNavHostController)
        }

        composable("MyProductsScreen") {
            MyProductsScreen(vendedoresViewModel, mainNavHostController)
        }

        composable("MyProductsScreenComprador") {
            MyProductsToBuyScreen(compradoresViewModel)
        }

        composable("QRGeneratorScreen") {
            QRGeneratorScreen(transaccionViewModel, mainNavHostController)
        }

        composable("TransaccionesPendientes") {
            TransaccionesPendientesScreen(transaccionViewModel, mainNavHostController)
        }

        composable(QRSCANNER) {
            QRScannerScreen(transaccionViewModel)
        }

        composable("ScreenProductsForSale") {
            ScreenProductsForSale(userViewModel, transaccionViewModel, mainNavHostController)
        }

        composable("SendingProductsScreen") {
            SendingProductsScreen(navHostController = mainNavHostController,transaccionViewModel = transaccionViewModel)
        }

        composable(route = "CompradorOfertaScreen/{idMensaje}",
            arguments = listOf(
                navArgument("idMensaje") { type = NavType.StringType })
        ) { backStackEntry ->
            val idMensaje = backStackEntry.arguments?.getString("idMensaje") ?: ""

            CompradorOfertaScreen (idMensaje, transaccionViewModel, mainNavHostController)
        }
    }
}

suspend fun getNextScreenAndKindUser(getUserPreferencesUseCase: GetUserPreferencesUseCase): Pair<String, Usuario> {
    val userPreferences = withContext(Dispatchers.IO) { getUserPreferencesUseCase.execute() }
    val nextScreen = if (userPreferences.nombre != "") PANTALLAPRINCIPAL else "login"
    val usuario = userPreferences

    return nextScreen to usuario
}