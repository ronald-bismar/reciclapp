package com.example.reciclapp.presentation.navigation

import CrearProductoReciclableComprador
import CrearProductoReciclableVendedor
import HistorialComprasScreen
import QRScannerScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.presentation.ui.CompartirScreen.CompartirScreen
import com.example.reciclapp.presentation.ui.aboutus.AboutUsScreen
import com.example.reciclapp.presentation.ui.contactate.ContactateConNosotrosScreen
import com.example.reciclapp.presentation.ui.drawer.ui.CalificanosScreen
import com.example.reciclapp.presentation.ui.drawer.ui.MisionVisionScreen
import com.example.reciclapp.presentation.ui.drawer.ui.SimpleAyudaScreen
import com.example.reciclapp.presentation.ui.login.ui.LoginScreen
import com.example.reciclapp.presentation.ui.login.ui.LoginViewModel
import com.example.reciclapp.presentation.ui.menu.ui.IntroductionScreen
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPresentacion
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPrincipal
import com.example.reciclapp.presentation.ui.menu.ui.PresentacionAppScreen
import com.example.reciclapp.presentation.ui.menu.ui.QRGeneratorDialog
import com.example.reciclapp.presentation.ui.menu.ui.SocialMediaScreenVendedores
import com.example.reciclapp.presentation.ui.menu.ui.TransaccionesPendientesScreen
import com.example.reciclapp.presentation.ui.menu.ui.UserTypeScreen
import com.example.reciclapp.presentation.ui.menu.ui.content.myproducts.MyProductsScreen
import com.example.reciclapp.presentation.ui.menu.ui.content.mypurchases.MyProductsToBuyScreen
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Comprador
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Perfil
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Vendedor
import com.example.reciclapp.presentation.ui.menu.ui.vistas.mapa.MapsView
import com.example.reciclapp.presentation.ui.registro.ui.RegistroScreen
import com.example.reciclapp.presentation.ui.registro.ui.RegistroViewModel
import com.example.reciclapp.presentation.ui.splash.SplashScreenContent
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    mainNavController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    registroViewModel: RegistroViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    vendedoresViewModel: VendedoresViewModel = hiltViewModel(),
    compradoresViewModel: CompradoresViewModel = hiltViewModel(),
    transaccionViewModel: TransaccionViewModel = hiltViewModel(),
    getUserPreferencesUseCase: GetUserPreferencesUseCase
) {
    var nextScreen by remember { mutableStateOf("splash") }
    var tipoDeUsuario by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val (screen, userType) = getNextScreenAndKindUser(getUserPreferencesUseCase)
        nextScreen = screen
        tipoDeUsuario = userType
    }

    NavHost(navController = mainNavController, startDestination = "splash") {
        composable("splash") {
            SplashScreenContent {
                mainNavController.navigate(nextScreen) {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("socialmediascreen") {
            SocialMediaScreenVendedores(mainNavController)
        }
        composable("login") {
            LoginScreen(loginViewModel, mainNavController)
        }
        composable("registro") {
            RegistroScreen(registroViewModel, mainNavController)
        }
        composable("menu") {
            PantallaPrincipal(mainNavController, tipoDeUsuario)
        }
        composable("pantalla presentacion") {
            PantallaPresentacion(mainNavController)
        }
        composable("presentacion app") {
            PresentacionAppScreen(mainNavController)
        }
        composable("introduction screen") {
            IntroductionScreen(mainNavController)
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
            Perfil(userViewModel = userViewModel, mainNavController)
        }
        composable("tipoDeUsuario") {
            UserTypeScreen(mainNavController)
        }
        composable(
            route = "compradorPerfil/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            Comprador(mainNavController = mainNavController, compradorId = userId)
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
                mainNavController = mainNavController,
                vendedorId = userId,
                productoId = productoId
            )
        }
        composable("map") {
            userViewModel.user.observeAsState().value?.idUsuario?.let { idUsuario ->
                MapsView(idUsuario = idUsuario, mainNavController = mainNavController)
            }
        }

        composable("AñadirProductoReciclable") {
            CrearProductoReciclableVendedor(mainNavController, vendedoresViewModel)
        }

        composable("AñadirProductoReciclableComprador") {
            CrearProductoReciclableComprador(mainNavController, compradoresViewModel)
        }

        composable("MyProductsScreen") {
            MyProductsScreen(mainNavController, vendedoresViewModel)
        }

        composable("MyProductsScreenComprador") {
            MyProductsToBuyScreen(mainNavController, compradoresViewModel)
        }

        composable("HistorialCompras") {
            HistorialComprasScreen(compradoresViewModel)
        }

        composable(
            "QRGenerator/{productoId}/{usuarioContactadoId}/{usuarioContactadoIsVendedor}",
            arguments = listOf(
                navArgument("productoId") { type = NavType.StringType },
                navArgument("usuarioContactadoId") { type = NavType.StringType },
                navArgument("usuarioContactadoIsVendedor") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
            val usuarioId = backStackEntry.arguments?.getString("usuarioContactadoId") ?: ""
            val usuarioContactadoIsVendedor =
                backStackEntry.arguments?.getBoolean("usuarioContactadoIsVendedor") == true

            QRGeneratorDialog(
                productoId = productoId,
                usuarioContactadoId = usuarioId,
                usuarioContactadoIsVendedor = usuarioContactadoIsVendedor,
                onDismiss = {},
                onContinue = {},
                transaccionViewModel = transaccionViewModel,

                )
        }

        composable("TransaccionesPendientes") {
            TransaccionesPendientesScreen(navController = mainNavController)
        }

        composable("QRScanner") {
             QRScannerScreen()
         }
    }
}

suspend fun getNextScreenAndKindUser(getUserPreferencesUseCase: GetUserPreferencesUseCase): Pair<String, String> {
    val userPreferences = withContext(Dispatchers.IO) { getUserPreferencesUseCase.execute() }
    val nextScreen = if (userPreferences?.nombre != "") "menu" else "login"
    val tipoDeUsuario = userPreferences?.tipoDeUsuario ?: ""

    return nextScreen to tipoDeUsuario
}