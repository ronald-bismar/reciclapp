package com.example.reciclapp_bolivia.presentation.ui.menu.ui.vistas

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp_bolivia.R
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.presentation.animations.AnimatedTransitionDialog
import com.example.reciclapp_bolivia.presentation.ui.registro.ui.photo_profile.SinglePhotoPicker
import com.example.reciclapp_bolivia.presentation.ui.registro.ui.showToast
import com.example.reciclapp_bolivia.presentation.viewmodel.UserViewModel
import com.example.reciclapp_bolivia.presentation.viewmodel.VendedoresViewModel
import com.example.reciclapp_bolivia.util.StorageUtil
import kotlinx.coroutines.launch
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Perfil(
    userViewModel: UserViewModel,
    vendedoresViewModel: VendedoresViewModel,
    navControllerMain: NavHostController
) {

    val context = LocalContext.current
    val user by userViewModel.user.observeAsState()
    val updateState by userViewModel.updateUserState.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(vendedoresViewModel) {
        vendedoresViewModel.showToast.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    user?.let { user ->

        Log.d("User, Perfil", user.toString())
        if (showDialog) {
            EditProfileDialog(
                onDismiss = { showDialog = false },
                user = user,
                userViewModel = userViewModel,
                context = LocalContext.current
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(user)
            ProfileActions(onEditClick = { showDialog = true })
            ProfileDetails(user)
            ProfileSettings(user)
            ProfileNuevoObjetoParaVender(navControllerMain, user)
        }
    }
    updateState?.let { result ->
        when {
            result.isSuccess -> {
                showToast(context, "Actualizacion exitosa")
                userViewModel.resetUpdateState()
            }

            result.isFailure -> {
                showToast(context, "Actualizacion fallida")
                userViewModel.resetUpdateState()
            }
        }
        userViewModel.resetUpdateUserState()
    }
}

@Composable
fun ProfileHeader(user: Usuario) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture(
                rememberAsyncImagePainter(model = if (user.urlImagenPerfil.isEmpty()) R.drawable.perfil else user.urlImagenPerfil),
                200.dp
            )
        }
    }
}

@Composable
fun ProfileDetails(user: Usuario) {
    ProfileSection("Account Details")
    ProfileItem("Name", user.nombre)
    ProfileItem("LastName", user.apellido)
    ProfileItem("Phone", user.telefono.toString())
    ProfileItem("Address", user.direccion)
    ProfileItem("Email", user.correo)
    ProfileItem("Points", "100")
}

@Composable
fun ProfileSettings(user: Usuario) {
    ProfileItem(
        "AccountType",
        user.tipoDeUsuario.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
}

@Composable
fun ProfileNuevoObjetoParaVender(navControllerMain: NavHostController, user: Usuario) {
    ProfileSection("Mi Reciclaje")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            // Botón para añadir nuevo objeto vendido
            Button(
                onClick = {
                    navControllerMain.navigate(
                        if ((user.tipoDeUsuario.uppercase()
                                ?: "") == "COMPRADOR"
                        ) "AñadirProductoReciclableComprador" else "AñadirProductoReciclable"
                    ) {
                        restoreState = true
                    }
                }, modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Añadir Nuevo Reciclaje Para ${if (user.tipoDeUsuario.uppercase() == "COMPRADOR") "Comprar" else "Vender"}")
            }
            // Botón para ver mis productos reciclables
            Button(
                onClick = {
                    if (user.tipoDeUsuario.uppercase() == "COMPRADOR") navControllerMain.navigate("MyProductsScreenComprador")
                    else navControllerMain.navigate("MyProductsScreen")
                }, modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_leaf),
                    contentDescription = "Ver mis productos reciclables"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mis productos")
            }

            Button(
                onClick = {
                    navControllerMain.navigate("TransaccionesPendientes")
                }, modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrows),
                    contentDescription = "Ver mis transacciones pendientes"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mis transacciones pendientes")
            }
        }
    }

}

@Composable
fun ProfileActions(onEditClick: () -> Unit) {
    ActionButton("Editar Perfil", Icons.Default.Edit, onEditClick)
}

@Composable
fun ProfilePicture(painter: Painter, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileSection(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.inverseSurface
        )
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun ActionButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditProfileDialog(
    onDismiss: () -> Unit, user: Usuario, userViewModel: UserViewModel, context: Context
) {
    var name by remember { mutableStateOf(user.nombre) }
    var lastName by remember { mutableStateOf(user.apellido) }
    var phone by remember { mutableStateOf(user.telefono.toString()) }
    var address by remember { mutableStateOf(user.direccion) }
    var email by remember { mutableStateOf(user.correo) }
    val isVendedor by userViewModel.isVendedor.observeAsState(user.tipoDeUsuario == "vendedor")
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(user.urlImagenPerfil)) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    AnimatedTransitionDialog(
        onDismissRequest = onDismiss, contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "Editar perfil",
                        fontSize = 20.sp,
                    )

                    EditProfileContent(
                        name = name,
                        onNameChange = { name = it },
                        lastName = lastName,
                        onLastNameChange = { lastName = it },
                        phone = phone,
                        onPhoneChange = { phone = it },
                        address = address,
                        onAddressChange = { address = it },
                        email = email,
                        onEmailChange = { email = it },
                        onImageUriChange = { imageUri = it },
                        user = user)

                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancelar")
                        }
                        TextButton(onClick = {
                            isLoading = true

                            coroutineScope.launch {
                                try {
                                    val url = imageUri?.let { uri ->
                                        StorageUtil.uploadToStorage(uri, context)
                                    }

                                    val dataUpdateUser = user.copy(
                                        nombre = name,
                                        apellido = lastName,
                                        telefono = phone.toLong(),
                                        direccion = address,
                                        correo = email,
                                        tipoDeUsuario = if (isVendedor) "vendedor" else "comprador",
                                        urlImagenPerfil = url ?: user.urlImagenPerfil
                                    )

                                    userViewModel.updateUser(dataUpdateUser)
                                } catch (e: Exception) {
                                    Log.e("MyComposable", "Error al subir la imagen", e)
                                } finally {
                                    // Finalizar el estado de carga
                                    isLoading = false
                                    onDismiss()
                                }
                            }
                        }) {
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EditProfileContent(
    name: String,
    onNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    onImageUriChange: (Uri?) -> Unit,
    user: Usuario,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SinglePhotoPicker(
            user = user,
            onImageUriReady = onImageUriChange,
            sizeImageProfile = 120,
            imageDefault = Uri.parse(user.urlImagenPerfil)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
            TextField(
                value = lastName,
                onValueChange = onLastNameChange,
                label = { Text("Last Name") })
            TextField(value = phone, onValueChange = onPhoneChange, label = { Text("Phone") })
            TextField(value = address, onValueChange = onAddressChange, label = { Text("Address") })
            TextField(
                value = email,
                onValueChange = onEmailChange,
                singleLine = true,
                label = { Text("Email") })
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}