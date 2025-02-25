package com.example.reciclapp.presentation.ui.menu.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reciclapp.presentation.navigation.AppTopBar
import com.example.reciclapp.presentation.navigation.bottom.BottomNavHost
import com.example.reciclapp.presentation.navigation.bottom.BottomSheetContent
import com.example.reciclapp.presentation.navigation.drawer.DrawerContent
import com.example.reciclapp.presentation.viewmodel.CompradoresViewModel
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.presentation.viewmodel.VendedoresViewModel
import kotlinx.coroutines.launch

/**
 * PantallaPrincipal es la función principal que configura la pantalla inicial del menú.
 * Utiliza varios componentes de Jetpack Compose para crear una interfaz de usuario
 * que incluye un Drawer de navegación, un Bottom Sheet y una Floating Action Button.
 *
 * @param navControllerMain El NavController principal para la navegación entre pantallas.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(navControllerMain: NavController, tipoDeUsuario: String?) {
    // ViewModel para manejar el estado del usuario
    val userViewModel: UserViewModel = hiltViewModel()
    val vendedoresViewModel: VendedoresViewModel = hiltViewModel()
    val compradoresViewModel: CompradoresViewModel = hiltViewModel()

    // Estado del Drawer de navegación
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    // Alcance para las corutinas
    val scope = rememberCoroutineScope()
    // Controlador de navegación para la navegación interna de la pantalla principal
    val navController = rememberNavController()
    // Estado del Bottom Sheet modal
    val bottomSheetState = rememberModalBottomSheetState()

    // Estado para manejar la carga del tipo de usuario
    var isLoading by remember { mutableStateOf(true) }

    // Observar cambios en el tipo de usuario
    LaunchedEffect(tipoDeUsuario) {
        if (tipoDeUsuario != null) {
            isLoading = false // El tipo de usuario ya está cargado
        }
    }

    // Elementos de navegación en el menú inferior
    val navigationItemsVendedor = listOf(
        ItemsMenu.Pantalla1,
        ItemsMenu.Pantalla2,
        ItemsMenu.Pantalla3, // Mapa
        ItemsMenu.Pantalla4,
        ItemsMenu.Pantalla5 // Posts
    )

    val navigationItemsComprador = listOf(
        ItemsMenu.Pantalla1,
        ItemsMenu.Pantalla2,
        ItemsMenu.Pantalla5, // Posts
        ItemsMenu.Pantalla4Vendedores,
        ItemsMenu.Pantalla3, // Mapa
    )

    // Determinar la navegación predeterminada según el tipo de usuario
    val navegacionPredeterminada = when (tipoDeUsuario?.uppercase()) {
        "COMPRADOR" -> navigationItemsComprador
        "VENDEDOR" -> navigationItemsVendedor
        else -> emptyList() // Si el tipo de usuario no está cargado, no mostrar botones
    }

    // Cargar productos del vendedor si es necesario
    LaunchedEffect(userViewModel.user.value) {
        userViewModel.user.value?.let {
            vendedoresViewModel.fetchProductosByVendedor(it.idUsuario)
        }
    }

    Log.d("MyUser", "MyUser: $tipoDeUsuario")

    // Mostrar el contenido principal
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DrawerContent(
                mainNavController = navControllerMain,
                onItemClick = { scope.launch { drawerState.close() } },
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    navControllerMain = navControllerMain,
                    navController = navController,
                    drawerState = drawerState,
                    scope = scope,
                    userViewModel = userViewModel
                )
            },
            bottomBar = {
                if (isLoading) {
                    // Mostrar un indicador de carga mientras se carga el tipo de usuario
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else {
                    // Mostrar los botones de navegación cuando el tipo de usuario esté cargado
                    MenuScreen(
                        navController = navController,
                        menuItems = navegacionPredeterminada,
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavHost(
                    mainNavController = navControllerMain,
                    navHostController = navController,
                    userViewModel.user?.value?.idUsuario ?: "",
                    userViewModel,
                    vendedoresViewModel.productos.collectAsState().value
                )
            }
        }
    }

    // Configuración del Bottom Sheet modal
    if (bottomSheetState.isVisible) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { scope.launch { bottomSheetState.hide() } }
        ) {
            BottomSheetContent()
        }
    }
}