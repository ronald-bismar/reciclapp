package com.example.reciclapp.presentation.navigation

import android.app.Activity
import android.util.Log
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import com.example.reciclapp.presentation.viewmodel.UserViewModel
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
    Log.d("UserViewModel", "userViewModelAppBar: $userViewModel")


    val logOutState by userViewModel.logOutState.observeAsState()


    val context = LocalContext.current

    // Estados para mostrar el menú desplegable y el diálogo
    var showMenu by remember { mutableStateOf(false) }

    // Barra superior de la aplicación
    TopAppBar(
        title = { Text(text = "ReciclApp") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            // Muestra la imagen del usuario si está disponible
            IconButton(onClick = {

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
            // Botón para abrir la configuración
        /*    IconButton(onClick = {
                Toast.makeText(context, "Botón configurar", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Botón configurar")
            }

         */
            // Botón para mostrar el menú desplegable
            IconButton(onClick = {
                showMenu = !showMenu
            }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Más opciones")
            }
            // Menú desplegable con opciones
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                    .width(165.dp)
                    .background(Color.White)
            ) {
                DropdownMenuItems(
                    navController = navController,
                    onMenuItemClick = { item ->
                        showMenu = !showMenu
                        Toast.makeText(context, item.message, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    )

    // Maneja el estado de logout
    logOutState?.let { result ->
        when {
            result.isSuccess -> {
                navControllerMain.navigate("login") {
                    popUpTo("menu") {
                        inclusive = true
                    } // Asegura que la pila de navegación se limpie
                }
            }

            result.isFailure -> {
                showToast(context, "No se pudo salir de la cuenta")
            }
        }
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
fun DropdownMenuItems(navController: NavHostController, onMenuItemClick: (MenuItem) -> Unit) {
    // Obtiene la instancia del ViewModel del usuario
    val userViewModel: UserViewModel = hiltViewModel()

    val context = LocalContext.current as Activity
    // Lista de ítems para el menú desplegable
    val items = listOf(

       // MenuItem("Mi perfil", Icons.Filled.Person, "Mi perfil"),
        MenuItem("Cerrar sesión", Icons.Filled.Face, "Cerrar sesión"),
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
                //   "Mi perfil" -> navController.navigate("perfil")
                    "Cambiar usuario" -> navController.navigate("premium")
                    "Cerrar sesión" -> {
                        userViewModel.logOutUser()
                    }

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
