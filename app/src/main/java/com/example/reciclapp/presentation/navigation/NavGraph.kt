package com.example.reciclapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.reciclapp.presentation.ui.login.ui.LoginScreen
import com.example.reciclapp.presentation.ui.login.ui.LoginViewModel
import com.example.reciclapp.presentation.ui.menu.ui.IntroductionScreen
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPresentacion
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Comprador
import com.example.reciclapp.presentation.ui.menu.ui.vistas.mapa.MapsView
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPrincipal
import com.example.reciclapp.presentation.ui.registro.ui.RegistroScreen
import com.example.reciclapp.presentation.ui.registro.ui.RegistroViewModel
import com.example.reciclapp.presentation.ui.splash.SplashScreenContent
import com.example.reciclapp.presentation.ui.menu.ui.vistas.Vendedor
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    registroViewModel: RegistroViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    getUserPreferencesUseCase: GetUserPreferencesUseCase
) {

    var nextScreen by remember {
        mutableStateOf("splash")
    }

    LaunchedEffect(Unit) {
        nextScreen = "pantalla presentacion"
//            getNextScreen(getUserPreferencesUseCase)
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreenContent {
                navController.navigate(nextScreen) {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
        composable("registro") {
            RegistroScreen(registroViewModel, navController)
        }
        composable("menu") {
            PantallaPrincipal(navController)
        }
        composable("pantalla presentacion") {
            PantallaPresentacion(navController)
        }
        composable("introduction screen"){
            IntroductionScreen(navController)
        }

        composable(
            route = "compradorPerfil/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            Comprador(navController = navController, compradorId = userId)
        }
        composable(
            route = "vendedorPerfil/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            Vendedor(navController = navController, vendedorId = userId)
        }
        composable("map"){
            userViewModel.user.observeAsState().value?.idUsuario?.let { idUsuario ->
                MapsView(idUsuario = idUsuario, mainNavController = navController)
            }
        }
    }
}

suspend fun getNextScreen(getUserPreferencesUseCase: GetUserPreferencesUseCase): String {
    return withContext(Dispatchers.IO) {
        if (getUserPreferencesUseCase.execute()?.nombre != "") "menu" else "login"
    }
}
