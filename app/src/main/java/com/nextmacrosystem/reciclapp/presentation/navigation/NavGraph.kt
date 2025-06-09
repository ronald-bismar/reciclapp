package com.nextmacrosystem.reciclapp.presentation.navigation

import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.components.CrearProductoReciclableVendedor

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
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.nextmacrosystem.reciclapp.presentation.ui.ayuda.ui.SimpleAyudaScreen
import com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui.ContactateConNosotrosScreen
import com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui.AboutUsScreen
import com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui.CalificanosScreen
import com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui.CompartirScreen
import com.nextmacrosystem.reciclapp.presentation.ui.drawer.ui.MisionVisionScreen
import com.nextmacrosystem.reciclapp.presentation.ui.login.ui.LoginScreen
import com.nextmacrosystem.reciclapp.presentation.ui.login.ui.LoginViewModel
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.ChatScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.CompradorOfertaScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.IntroductionScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.PantallaPresentacion
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.PantallaPrincipal
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.PresentacionAppScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.QRGeneratorScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.QRScannerScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.RecyclableClassifierApp
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.ScreenProductsForSale
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.ScreenProductsForSaleVendedor
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.TransaccionesPendientesScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.UserTypeScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.myproducts.MyProductsScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.myproducts.MyProductsToBuyScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.Comprador
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.MessagesScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.Perfil
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.SendingProductsScreen
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.Vendedor
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.components.CrearProductoReciclableComprador
import com.nextmacrosystem.reciclapp.presentation.ui.registro.ui.RegistroScreen
import com.nextmacrosystem.reciclapp.presentation.ui.registro.ui.RegistroViewModel
import com.nextmacrosystem.reciclapp.presentation.ui.registro.ui.UploadImageScreen
import com.nextmacrosystem.reciclapp.presentation.ui.splash.SplashScreenContent
import com.nextmacrosystem.reciclapp.presentation.viewmodel.ClassifierViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.MensajeViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.UserViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.VendedoresViewModel
import com.nextmacrosystem.reciclapp.util.NameRoutes.CHATSCREEN
import com.nextmacrosystem.reciclapp.util.NameRoutes.MESSAGESSCREEN
import com.nextmacrosystem.reciclapp.util.NameRoutes.PANTALLAPRESENTACION
import com.nextmacrosystem.reciclapp.util.NameRoutes.PANTALLAPRINCIPAL
import com.nextmacrosystem.reciclapp.util.NameRoutes.QRSCANNER
import com.nextmacrosystem.reciclapp.util.NameRoutes.RECYCLABLECLASSIFIERSCREEN
import com.nextmacrosystem.reciclapp.util.NameRoutes.UPLOADIMAGEPROFILESCREEN

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
    val mensajeViewModel: MensajeViewModel = hiltViewModel()
    val classifierViewModel: ClassifierViewModel = hiltViewModel()

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
        composable(PANTALLAPRESENTACION) {
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
            Comprador(
                mainNavHostController,
                userId,
                compradoresViewModel,
                transaccionViewModel,
                mensajeViewModel
            )
        }
        composable(
            route = "VendedorPerfil/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            Vendedor(
                mainNavHostController, userId, vendedoresViewModel,
                transaccionViewModel,
                mensajeViewModel
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
            QRGeneratorScreen(transaccionViewModel, mensajeViewModel, mainNavHostController)
        }

        composable("TransaccionesPendientes") {
            TransaccionesPendientesScreen(transaccionViewModel, mainNavHostController)
        }

        composable(QRSCANNER) {
            QRScannerScreen(transaccionViewModel)
        }

        composable("ScreenProductsForSale") {
            ScreenProductsForSale(
                userViewModel,
                transaccionViewModel,
                mensajeViewModel,
                mainNavHostController
            )
        }

        composable("ScreenProductsForSaleVendedor") {
            ScreenProductsForSaleVendedor(
                vendedoresViewModel,
                transaccionViewModel,
                mensajeViewModel,
                mainNavHostController
            )
        }

        composable("SendingProductsScreen") {
            SendingProductsScreen(
                navHostController = mainNavHostController,
                transaccionViewModel = transaccionViewModel,
                mensajeViewModel = mensajeViewModel
            )
        }

        composable(
            route = "CompradorOfertaScreen/{idMensaje}",
            arguments = listOf(
                navArgument("idMensaje") { type = NavType.StringType })
        ) { backStackEntry ->
            val idMensaje = backStackEntry.arguments?.getString("idMensaje") ?: ""

            CompradorOfertaScreen(idMensaje, mensajeViewModel, mainNavHostController)
        }

        composable(
            "$CHATSCREEN?idMensaje={idMensaje}",
            arguments = listOf(
                navArgument("idMensaje") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val idMensaje = backStackEntry.arguments?.getString("idMensaje") ?: ""
            ChatScreen(idMensaje, mainNavHostController, mensajeViewModel)
        }

        composable(MESSAGESSCREEN) {
            MessagesScreen(mensajeViewModel, mainNavHostController)
        }

        composable(RECYCLABLECLASSIFIERSCREEN) {
            RecyclableClassifierApp(classifierViewModel)
        }

        composable(UPLOADIMAGEPROFILESCREEN){
            UploadImageScreen(registroViewModel, mainNavHostController)
        }
    }
}

suspend fun getNextScreenAndKindUser(getUserPreferencesUseCase: GetUserPreferencesUseCase): Pair<String, Usuario> {
    val userPreferences = withContext(Dispatchers.IO) { getUserPreferencesUseCase.execute() }
    val nextScreen = if (userPreferences.nombre != "") PANTALLAPRINCIPAL else "login"
    val usuario = userPreferences

    return nextScreen to usuario
}