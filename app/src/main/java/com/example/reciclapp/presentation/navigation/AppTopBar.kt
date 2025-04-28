package com.example.reciclapp.presentation.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.R
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.util.NameRoutes.MESSAGESSCREEN
import com.example.reciclapp.util.NameRoutes.QRSCANNER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Composable que muestra la barra superior de la aplicación.
 *
 * @param navControllerMain Controlador de navegación principal.
 * @param navController Controlador de navegación de la pantalla actual.
 * @param drawerState Estado del cajón de navegación (drawer).
 * @param scope Alcance de las corutinas para manejar el cajón de navegación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navControllerMain: NavController,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    userViewModel: UserViewModel
) {
    // Observa el estado del usuario y del logout
    val user by userViewModel.user.observeAsState()


    val logOutState by userViewModel.logOutState.observeAsState()


    val context = LocalContext.current

    // Estados para mostrar el menú desplegable y el diálogo
    var showMenu by remember { mutableStateOf(false) }

    // Barra superior de la aplicación
    TopAppBar(
        title = { Text(text = "ReciclApp") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.surface
        ),
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    Icons.Filled.Menu,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = "Menú"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navControllerMain.navigate(MESSAGESSCREEN) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = "Messages by products"
                )
            }

            IconButton(onClick = {
                navControllerMain.navigate(QRSCANNER) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_scanner_qr),
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = "Subir nuevo producto"
                )
            }
            IconButton(onClick = {
                navControllerMain.navigate(
                    if ((user?.tipoDeUsuario?.uppercase()
                            ?: "") == "COMPRADOR"
                    ) "AñadirProductoReciclableComprador" else "AñadirProductoReciclable"
                ) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = "Subir nuevo producto"
                )
            }
            // Muestra la imagen del usuario si está disponible
            IconButton(onClick = {
                val route = "perfil"
                navControllerMain.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                user?.let { user ->
                    val painter = rememberAsyncImagePainter(model = user.urlImagenPerfil)
                    Image(
                        painter = painter,
                        contentDescription = "Imagen del usuario",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Botón para mostrar el menú desplegable
            IconButton(onClick = {
                showMenu = !showMenu
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = "Más opciones"
                )
            }
            // Menú desplegable con opciones
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                    .width(165.dp)
                    .background(Color.White),
            ) {
                DropdownMenuItems(
                    navController = navController,
                    onMenuItemClick = { item ->
                        showMenu = !showMenu
                        Toast.makeText(context, item.message, Toast.LENGTH_SHORT).show()
                    }, userViewModel
                )
            }
        },
        //  modifier = Modifier.height(30.dp)
    )

    // Maneja el estado de logout
    logOutState?.let { result ->
        when {
            result.isSuccess -> {
                navControllerMain.navigate("login") {
                    popUpTo("menu") {
                        inclusive = true
                    }
                }
            }

            result.isFailure -> {
                showToast(context, "No se pudo salir de la cuenta")
            }
        }
        userViewModel.resetStateLogout()
    }
}

/**
 * Data class que representa un ítem del menú desplegable.
 *
 * @param text Texto que se muestra en el ítem del menú.
 * @param icon Icono que se muestra en el ítem del menú.
 * @param message Mensaje asociado al ítem del menú.
 */
data class MenuItem(
    val text: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val message: String
)

/**
 * Composable que muestra los ítems del menú desplegable.
 *
 * @param navController Controlador de navegación de la pantalla actual.
 * @param onMenuItemClick Función de callback que se llama cuando se hace clic en un ítem del menú.
 */
@Composable
fun DropdownMenuItems(
    navController: NavHostController,
    onMenuItemClick: (MenuItem) -> Unit,
    userViewModel: UserViewModel
) {

    val context = LocalContext.current as Activity
    val items = listOf(

        MenuItem("Cerrar sesión", Icons.Filled.Face, "Cerrando sesión"),
        MenuItem("Salir", Icons.Filled.Close, "Salir")
    )
    items.forEach { item ->
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color.White),
            onClick = {
                when (item.text) {
                    "Cerrar sesión" -> userViewModel.logOutUser()
                    "Salir" -> context.finishAffinity()
                }
                onMenuItemClick(item)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.text, color = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = item.icon, contentDescription = item.text, tint = Color.Black)
            }
        }
    }
}