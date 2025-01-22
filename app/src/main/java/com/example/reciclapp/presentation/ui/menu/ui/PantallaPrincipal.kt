package com.example.reciclapp.presentation.ui.menu.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reciclapp.presentation.navigation.*
import com.example.reciclapp.presentation.navigation.bottom.BottomNavHost
import com.example.reciclapp.presentation.navigation.bottom.BottomSheetContent
import com.example.reciclapp.presentation.navigation.drawer.DrawerContent
import com.example.reciclapp.presentation.viewmodel.UserViewModel
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
fun PantallaPrincipal(navControllerMain: NavController) {
    // ViewModel para manejar el estado del usuario
    // (Esta instancia de View Model se envia a todos los hijos para que recuerden y actualicen el estado cuando haya cambios)
    val userViewModel: UserViewModel = hiltViewModel()
    // Estado del Drawer de navegación
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    // Alcance para las corutinas
    val scope = rememberCoroutineScope()
    // Controlador de navegación para la navegación interna de la pantalla principal
    val navController = rememberNavController()
    // Estado del Bottom Sheet modal
    val bottomSheetState = rememberModalBottomSheetState()
    // Estado para controlar la visibilidad del menú desplegable
    var showDropdownMenu by remember { mutableStateOf(false) }

    // Elementos de navegación en el menú inferior
    val navigationItems = listOf(
        ItemsMenu.Pantalla1,
        ItemsMenu.Pantalla2,
        ItemsMenu.Pantalla3,
        ItemsMenu.Pantalla4,
        ItemsMenu.Pantalla5
    )

    // Configuración del Drawer de navegación
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                mainNavController = navControllerMain,
                onItemClick = { scope.launch { drawerState.close() } },
            )
        }
    ) {
        // Configuración del Scaffold para la pantalla principal
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
                MenuScreen(
                    navController = navController,
                    menuItems = navigationItems,
                    userViewModel = userViewModel
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {  navControllerMain.navigate("map") },
                    containerColor = MaterialTheme.colorScheme.primary, // Cambiar el color del botón flotante
                    contentColor = MaterialTheme.colorScheme.onSurface // Cambiar el color del icono dentro del botón
                ) {
                    Icon(Icons.Default.Place, contentDescription = "Maps View")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavHost(
                    mainNavController = navControllerMain,
                    navHostController = navController,
                    userViewModel.user?.value.let { it?.idUsuario } ?: 0
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
