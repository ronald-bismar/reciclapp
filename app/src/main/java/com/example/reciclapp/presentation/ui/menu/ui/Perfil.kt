package com.example.reciclapp.presentation.ui.menu.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.reciclapp.util.StorageUtil
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.ui.registro.ui.UserType
import com.example.reciclapp.presentation.ui.registro.ui.photo_profile.SinglePhotoPicker
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import com.example.reciclapp.presentation.viewmodel.UserViewModel

@Composable
fun Perfil(userViewModel: UserViewModel) {
val context = LocalContext.current
    val user by userViewModel.user.observeAsState()
    val updateState by userViewModel.updateUserState.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    user?.let {
        if (showDialog) {
            EditProfileDialog(onDismiss = { showDialog = false }, user = it, userViewModel = userViewModel, context = LocalContext.current)
        }
        ProfileContent(user = it, onEditClick = { showDialog = true })
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
    }
}

@Composable
fun ProfileContent(user: Usuario, onEditClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(user)
        ProfileActions(onEditClick)
        ProfileDetails(user)
        ProfileSettings(user)

    }
}

@Composable
fun ProfileHeader(user: Usuario) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture(rememberAsyncImagePainter(model = user.urlImagenPerfil), 200.dp)
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
    ProfileSection("Settings")
    ProfileItem("AccountType", user.tipoDeUsuario)
    ProfileItem("Privacy", "Public")
    ProfileItem("Notifications", "Enabled")
}

@Composable
fun ProfileActions(onEditClick: () -> Unit) {
    /*ProfileSection("Actions")*/
    ActionButton("Edit Profile", Icons.Default.Edit, onEditClick)
}

@Composable
fun ProfilePicture(painter: Painter, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
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
            color = Color.Black
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
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}

@Composable
fun EditProfileDialog(onDismiss: () -> Unit, user: Usuario, userViewModel: UserViewModel, context: Context) {
    var name by remember { mutableStateOf(user.nombre) }
    var lastName by remember { mutableStateOf(user.apellido) }
    var phone by remember { mutableStateOf(user.telefono.toString()) }
    var address by remember { mutableStateOf(user.direccion) }
    var email by remember { mutableStateOf(user.correo) }
    val isVendedor by userViewModel.isVendedor.observeAsState(false)
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(user.urlImagenPerfil)) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar perfil") },
        text = {
            if (isLoading) {
                // Muestra el CircularProgressIndicator si está en progreso
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
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
                    user = user,
                    isVendedor = isVendedor,
                    onIsVendedorChanged = userViewModel::onIsVendedorChanged
                )
            }
        },
        confirmButton = {
            if (!isLoading) {
                TextButton(onClick = {
                    isLoading = true
                    StorageUtil.uploadToStorage(imageUri!!, context) { url ->
                        imageUrl = url
                        val dataUpdateUser = user.copy(
                            nombre = name,
                            apellido = lastName,
                            telefono = phone.toLong(),
                            direccion = address,
                            correo = email,
                            tipoDeUsuario = if (isVendedor) "vendedor" else "comprador",
                            urlImagenPerfil = imageUrl ?: user.urlImagenPerfil
                        )
                        userViewModel.updateUser(dataUpdateUser)
                        isLoading = false
                        onDismiss()
                    }
                }) {
                    Text("Guardar")
                }
            }
        },
        dismissButton = {
            if (!isLoading) {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        }
    )
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
    isVendedor: Boolean,
    onIsVendedorChanged: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SinglePhotoPicker(
            user = user,
            onImageUriReady = onImageUriChange,
            sizeImageProfile = 100,
            imageDefault = Uri.parse(user.urlImagenPerfil)
        )
        UserType(
            Modifier.align(Alignment.CenterHorizontally),
            isVendedor,
            onIsVendedorChanged
        )
        TextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
        TextField(value = lastName, onValueChange = onLastNameChange, label = { Text("Last Name") })
        TextField(value = phone, onValueChange = onPhoneChange, label = { Text("Phone") })
        TextField(value = address, onValueChange = onAddressChange, label = { Text("Address") })
        TextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
    }
}
