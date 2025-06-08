<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/navigation/drawer/Screens.kt
package com.example.reciclapp_bolivia.presentation.navigation.drawer
========
package com.nextmacrosystem.reciclapp.presentation.navigation.drawer
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/navigation/drawer/Screens.kt

sealed class Screens(var route: String) {
    data object  Home : Screens("home")
    data object  Profile : Screens("profile")
    data object  Notification : Screens("notification")
    data object  Setting : Screens("setting")
}