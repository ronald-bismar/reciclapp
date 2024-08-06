package com.example.reciclapp.presentation.ui.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reciclapp.presentation.navigation.*
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

/**
 * PantallaPrincipal es la función principal que configura la pantalla inicial del menú.
 * Utiliza varios componentes de Jetpack Compose para crear una interfaz de usuario
 * que incluye un Drawer de navegación, un Bottom Sheet y una Floating Action Button.
 *
 * @param navControllerMain El NavController principal para la navegación entre pantallas.
 */
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
                navController = navController,
                onItemClick = { scope.launch { drawerState.close() } },
                userViewModel = userViewModel
            )
        }
    ) {
        // Configuración del Scaffold para la pantalla principal
        Scaffold(
            topBar = {
                AppTopBar(
                    navControllerMain = navControllerMain,
                    navController = navController,
                    showDropdownMenu = showDropdownMenu,
                    setShowDropdownMenu = { showDropdownMenu = it },
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
                    onClick = {
                        navControllerMain.navigate("map")
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Abrir Bottom Sheet")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavHost(
                    navController = navControllerMain,
                    navHostController = navController,
                    userViewModel = userViewModel
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
