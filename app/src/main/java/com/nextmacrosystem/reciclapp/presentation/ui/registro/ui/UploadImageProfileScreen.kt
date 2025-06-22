package com.nextmacrosystem.reciclapp.presentation.ui.registro.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.nextmacrosystem.reciclapp.presentation.ui.registro.ui.photo_profile.SinglePhotoPicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadImageScreen(viewModel: RegistroViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val registerState by viewModel.registerState.observeAsState()
    var recordEnable by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.observeAsState(false)
    val defaultUri = "android.resource://com.nextmacrosystem.reciclapp/drawable/perfil".toUri()
    var imageUri by remember { mutableStateOf<Uri?>(defaultUri) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Foto de perfil", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Sube una imagen de perfil para personalizar tu cuenta y facilitar tu identificación en la app. Si no lo haces, se utilizará la imagen predeterminada.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.weight(1f))
                SinglePhotoPicker(onImageUriReady = { uri ->
                    recordEnable = uri != null
                    imageUri = uri
                }, sizeImageProfile = 150, context = context)
                Spacer(modifier = Modifier.weight(1f))
                RegistroButton(isLoading, recordEnable = recordEnable, onRecordSelected = {
                    viewModel.updateProfileImage(imageUri, context)
                })

                registerState?.let { result ->
                    when {
                        result.isSuccess -> {
                            navController.navigate("PantallaPresentacion")
                        }

                        result.isFailure -> {
                            showToast(
                                context,
                                "Error en el registro: ${result.exceptionOrNull()?.message}"
                            )
                        }
                    }
                    viewModel.resetState()
                }
                Spacer(modifier = Modifier.height(10.dp))
                OmiteImageProfile(Modifier.align(Alignment.End), navController)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun OmiteImageProfile(modifier: Modifier, navController: NavHostController) {
    Text(
        text = "Omitir por ahora", modifier = modifier.clickable {
            navController.navigate("PantallaPresentacion")
        }, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF174D11)
    )
}

