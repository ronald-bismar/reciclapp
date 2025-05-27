package com.nextmacrosystem.reciclapp.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DrawerState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.R
import com.example.reciclapp.presentation.viewmodel.UserViewModel
import com.example.reciclapp.util.NameRoutes.MESSAGESSCREEN
import com.example.reciclapp.util.NameRoutes.QRSCANNER
import com.example.reciclapp.util.NameRoutes.RECYCLABLECLASSIFIERSCREEN
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

    val user by userViewModel.user.observeAsState()

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

            IconButton(onClick = {
                navControllerMain.navigate(RECYCLABLECLASSIFIERSCREEN)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
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
        }
    )
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