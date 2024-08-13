package com.example.reciclapp.presentation.navigation.drawer

sealed class Screens(var route: String) {
    data object  Home : Screens("home")
    data object  Profile : Screens("profile")
    data object  Notification : Screens("notification")
    data object  Setting : Screens("setting")
}