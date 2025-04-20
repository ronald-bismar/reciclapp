package com.example.reciclapp

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.presentation.navigation.NavGraph
import com.example.reciclapp.presentation.ui.common.InAppNotification
import com.example.reciclapp.theme.ReciclAppTheme
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

                    // Añadir InAppNotification en la parte superior de la jerarquía
                    InAppNotification { mensaje ->
                        Log.d("NotificationEntry", "Message received: $mensaje")
                        val idProductosConPrecio: String = mensaje.idProductoConPrecio
                        val idVendedor: String = mensaje.idVendedor
                        val idComprador: String = mensaje.idComprador

                        Log.d("NotificationEntry", "idProductosConPrecio: $idProductosConPrecio")
                        Log.d("NotificationEntry", "idVendedor: $idVendedor")
                        Log.d("NotificationEntry", "idComprador: $idComprador")

                        navController?.let {
                            navController!!.navigate("CompradorOfertaScreen/$idProductosConPrecio/$idVendedor/$idComprador")
                        }
                    }
                }
            }
        }
    }
}
