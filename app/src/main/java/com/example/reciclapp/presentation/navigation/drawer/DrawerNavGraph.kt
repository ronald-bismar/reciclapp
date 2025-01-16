package com.example.reciclapp.presentation.navigation.drawer
/*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciclapp.presentation.navigation.Drawer.screens.PerfilScreen
import com.example.reciclapp.presentation.navigation.drawer.screens.ContactScreen
import com.example.reciclapp.presentation.navigation.drawer.screens.NotificationScreen
import com.meet.navigationdrawerjc.screens.ProfileScreen
import com.meet.navigationdrawerjc.screens.SettingScreen

@Composable
fun DrawerNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController,
        startDestination = Screens.Home.route){

        composable(Screens.Home.route){
            com.meet.navigationdrawerjc.screens.HomeScreen(innerPadding = innerPadding)
        }
        composable(Screens.Notification.route){
            NotificationScreen(innerPadding = innerPadding)
        }
        composable(Screens.Profile.route){
            ProfileScreen(innerPadding = innerPadding)
        }

        composable(Screens.Setting.route){
            SettingScreen(innerPadding = innerPadding)
        }
        composable("contact") {
            ContactScreen()
        }
        composable("perfil") {
            PerfilScreen()
        }
    }
}

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Notification : Screens("notification")
    object Profile : Screens("profile")
 */
