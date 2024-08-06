package com.example.reciclapp.presentation.navigation

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
import com.example.reciclapp.presentation.ui.menu.ui.Comprador
import com.example.reciclapp.presentation.ui.menu.ui.MapsView
import com.example.reciclapp.presentation.ui.menu.ui.PantallaPrincipal
import com.example.reciclapp.presentation.ui.registro.ui.RegistroScreen
import com.example.reciclapp.presentation.ui.registro.ui.RegistroViewModel
import com.example.reciclapp.presentation.ui.splash.SplashScreenContent
import com.example.reciclapp.presentation.ui.menu.ui.Vendedor
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        nextScreen = getNextScreen(getUserPreferencesUseCase)
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
            userViewModel.user.observeAsState().value?.idUsuario?.let { it1 ->
                MapsView(idUsuario = it1)
            }
        }
    }
}

suspend fun getNextScreen(getUserPreferencesUseCase: GetUserPreferencesUseCase): String {
    return withContext(Dispatchers.IO) {
        if (getUserPreferencesUseCase.execute()?.nombre != "") "menu" else "login"
    }
}
