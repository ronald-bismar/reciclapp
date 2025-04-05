package com.example.reciclapp.presentation.ui.menu.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reciclapp.presentation.navigation.AppTopBar
import com.example.reciclapp.presentation.navigation.bottom.BottomNavHost
import com.example.reciclapp.presentation.navigation.drawer.DrawerContent
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.UbicacionViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import com.example.reciclapp.util.ItemsMenu
import com.example.reciclapp.util.TipoDeUsuario
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

/**
 * PantallaPrincipal es la función principal que configura la pantalla inicial del menú.
 * Utiliza varios componentes de Jetpack Compose para crear una interfaz de usuario
 * que incluye un Drawer de navegación.
 *
 * @param navHostControllerMain El NavController principal para la navegación entre pantallas.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PantallaPrincipal(
    userViewModel: UserViewModel,
    vendedoresViewModel: VendedoresViewModel,
    compradoresViewModel: CompradoresViewModel,
    ubicacionViewModel: UbicacionViewModel,
    navHostControllerMain: NavController,
) {

    val context = LocalContext.current

    if (userViewModel.user.value?.tokenNotifications?.isEmpty() == true)
        getToken(userViewModel)

    userViewModel.loadUserPreferences()

    val usuarioLogueado = userViewModel.user.value
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    if (usuarioLogueado?.idUsuario == "") {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        // Data MapsView.kt
        val locationPermissionState =
            rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

        LaunchedEffect(locationPermissionState.status) {
            if (locationPermissionState.status.isGranted) {
                ubicacionViewModel.fetchLocationsAndUsers()
                ubicacionViewModel.obtenerYGuardarMiUbicacion(usuarioLogueado!!.idUsuario)
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        }

        // Data BuyersScreen.kt
        when (usuarioLogueado!!.tipoDeUsuario.uppercase()) {
            TipoDeUsuario.VENDEDOR -> userViewModel.fetchCompradores()
        }

        //Data SocialMediaScreenVendedores.kt
        vendedoresViewModel.fetchAllProductsAndVendedor()

        //Data StatisticsScreen.kt (para sacar stadisticas de los productos)
        userViewModel.fetchProductosByVendedor(usuarioLogueado)

        /**Datos para pantallas como comprador**/

        // RankingScreen.kt y BuyersScreen.kt como comprador
        when (usuarioLogueado.tipoDeUsuario.uppercase()) {
            TipoDeUsuario.COMPRADOR -> userViewModel.fetchVendedores()
        }

        // HistorialComprasScreen.kt
        compradoresViewModel.fetchProductosByComprador(usuarioLogueado.idUsuario)


        val navigationItemsVendedor = listOf(
            ItemsMenu.PantallaV1,
            ItemsMenu.PantallaV2,
            ItemsMenu.PantallaV3,
            ItemsMenu.PantallaV4,
            ItemsMenu.PantallaV5
        )

        val navigationItemsComprador = listOf(
            ItemsMenu.PantallaC1,
            ItemsMenu.PantallaC2,
            ItemsMenu.PantallaC3,
            ItemsMenu.PantallaC4,
            ItemsMenu.PantallaC5,
        )

        val navegacionPredeterminada =
            when (usuarioLogueado.tipoDeUsuario.uppercase()) {
                TipoDeUsuario.COMPRADOR -> navigationItemsComprador
                TipoDeUsuario.VENDEDOR -> navigationItemsVendedor
                else -> emptyList()
            }

        val startDestination =
            if (navegacionPredeterminada.size > 2) navegacionPredeterminada[2].ruta else ItemsMenu.PantallaV3.ruta

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                DrawerContent(
                    mainNavController = navHostControllerMain,
                    onItemClick = { scope.launch { drawerState.close() } },
                )
            }
        ) {
            Scaffold(
                topBar = {
                    AppTopBar(
                        navControllerMain = navHostControllerMain,
                        navController = navController,
                        drawerState = drawerState,
                        scope = scope,
                        userViewModel = userViewModel
                    )
                },
                bottomBar = {
                    MenuScreen(
                        navController = navController,
                        menuItems = navegacionPredeterminada,
                    )

                },
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    BottomNavHost(
                        mainNavController = navHostControllerMain,
                        navHostController = navController,
                        userViewModel = userViewModel,
                        ubicacionViewModel = ubicacionViewModel,
                        vendedoresViewModel = vendedoresViewModel,
                        compradoresViewModel = compradoresViewModel,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}

@Composable
private fun getToken(userViewModel: UserViewModel) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("FirebaseMesagging", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result
        userViewModel.updateToken(token)
    })
}
