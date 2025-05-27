package com.nextmacrosystem.reciclapp.presentation.ui.registro.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reciclapp.R
import com.example.reciclapp.presentation.ui.menu.ui.vistas.components.HeaderImageLogoReciclapp
import com.example.reciclapp.presentation.ui.menu.ui.vistas.components.LoadingButton
import com.example.reciclapp.presentation.ui.registro.ui.photo_profile.SinglePhotoPicker
import com.example.reciclapp.util.StorageUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(viewModel: RegistroViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val registerState by viewModel.registerState.observeAsState()
    val name by viewModel.name.observeAsState("")
    val lastName by viewModel.lastname.observeAsState("")
    val phone by viewModel.phone.observeAsState("")
    val address by viewModel.address.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val recordEnable by viewModel.recordEnable.observeAsState(false)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    val defaultUri = Uri.parse("android.resource://com.example.reciclapp/drawable/perfil")
    var imageUri by remember { mutableStateOf<Uri?>(defaultUri) }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                HeaderImageLogoReciclapp(
                    modifier = Modifier
                        .size(width = 300.dp, height = 60.dp)
                        .align(Alignment.CenterHorizontally)
                )
                SinglePhotoPicker(onImageUriReady = { uri ->
                    imageUri = uri
                }, sizeImageProfile = 150)
                NameField(name) { newName ->
                    viewModel.onRecordChanged(
                        newName, lastName, phone, address, email, password
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                LastNameField(lastName) { newLastName ->
                    viewModel.onRecordChanged(
                        name, newLastName, phone, address, email, password
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                PhoneField(phone) { newPhone ->
                    viewModel.onRecordChanged(
                        name, lastName, newPhone, address, email, password
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                AddressField(address) { newAddress ->
                    viewModel.onRecordChanged(
                        name, lastName, phone, newAddress, email, password
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                EmailField(email) { newEmail ->
                    viewModel.onRecordChanged(
                        name, lastName, phone, address, newEmail, password
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                PasswordField(password) { newPassword ->
                    viewModel.onRecordChanged(
                        name, lastName, phone, address, email, newPassword
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                RegistroButton(isLoading, recordEnable = recordEnable, onRecordSelected = {
                    viewModel.initLoading()
                    coroutineScope.launch {
                        val url = imageUri.let {
                            StorageUtil.uploadToStorage(it!!, context)
                        }
                        viewModel.onRecordSelected(
                            name,
                            lastName,
                            phone.toLong(),
                            address,
                            email,
                            password,
                            url ?: ""
                        )
                        viewModel.stopLoading()
                    }
                })

                registerState?.let { result ->

                    Log.d("Estado", "result: $result")
                    when {
                        result.isSuccess -> {
                            navController.navigate("pantalla presentacion")
                            viewModel.resetState()
                        }

                        result.isFailure -> {
                            showToast(
                                context,
                                "Error en el registro: ${result.exceptionOrNull()?.message}"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                AccountButton(Modifier.align(Alignment.End), navController)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun RegistroButton(isLoading: Boolean, recordEnable: Boolean, onRecordSelected: () -> Unit) {
    LoadingButton(
        isLoading = isLoading,
        buttonText = "Registro",
        onClick = { onRecordSelected() },
        enabled = recordEnable
    )
}

@Composable
fun AccountButton(modifier: Modifier, navController: NavHostController) {
    Text(
        text = "¿Ya tienes cuenta? LOGIN", modifier = modifier.clickable {
            navController.navigate("login")
        }, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF174D11)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onEmailChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChanged,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onPasswordChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChanged,
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = TextStyle(fontSize = 16.sp),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = painterResource(id = if (passwordVisibility) R.drawable.invisible else R.drawable.ojo),
                    contentDescription = if (passwordVisibility) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = if (passwordVisibility) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(name: String, onNameChanged: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChanged,
        label = { Text("Nombre") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LastNameField(lastName: String, onLastNameChanged: (String) -> Unit) {
    OutlinedTextField(
        value = lastName,
        onValueChange = onLastNameChanged,
        label = { Text("Apellido") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneField(phone: String, onPhoneChanged: (String) -> Unit) {
    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChanged,
        label = { Text("Teléfono") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressField(address: String, onAddressChanged: (String) -> Unit) {
    OutlinedTextField(
        value = address,
        onValueChange = onAddressChanged,
        label = { Text("Dirección") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}
