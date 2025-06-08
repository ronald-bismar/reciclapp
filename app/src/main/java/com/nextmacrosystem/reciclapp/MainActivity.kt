<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/MainActivity.kt
package com.example.reciclapp_bolivia
========
package com.nextmacrosystem.reciclapp
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/MainActivity.kt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/MainActivity.kt
import com.example.reciclapp_bolivia.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp_bolivia.presentation.navigation.NavGraph
import com.example.reciclapp_bolivia.presentation.ui.menu.ui.vistas.components.InAppNotification
import com.example.reciclapp_bolivia.theme.ReciclAppTheme
import com.example.reciclapp_bolivia.util.NameRoutes.CHATSCREEN
import com.example.reciclapp_bolivia.util.NameRoutes.PANTALLAPRINCIPAL
========
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.nextmacrosystem.reciclapp.presentation.navigation.NavGraph
import com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.components.InAppNotification
import com.nextmacrosystem.reciclapp.theme.ReciclAppTheme
import com.nextmacrosystem.reciclapp.util.NameRoutes.CHATSCREEN
import com.nextmacrosystem.reciclapp.util.NameRoutes.PANTALLAPRINCIPAL
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/MainActivity.kt
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var navController: NavHostController? = null

    @Inject
    lateinit var getUserPreferencesUseCase: GetUserPreferencesUseCase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ReciclAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        navController = rememberNavController()
                        NavGraph(
                            mainNavHostController = navController!!,
                            getUserPreferencesUseCase = getUserPreferencesUseCase
                        )
                    }

                    InAppNotification(
                        // Para notificaciones regulares
                        onNotificationClick = { mensaje ->
                            navController?.let {
                                navController!!.navigate("CompradorOfertaScreen/${mensaje.idMensaje}")
                            }
                        },
                        // Para notificaciones de oferta aceptada (botón de ubicación)
                        onNotificationAccepted = {
                            navController?.let {
                                navController!!.navigate(PANTALLAPRINCIPAL)
                            }
                        }, onSendNewMessage = { mensaje ->
                            navController?.navigate("$CHATSCREEN?idMensaje=${mensaje.idMensaje}")
                        }
                    )
                }
            }
        }
    }
}
/*3*/