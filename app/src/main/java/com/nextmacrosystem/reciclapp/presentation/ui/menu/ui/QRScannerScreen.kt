package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.nextmacrosystem.reciclapp.presentation.viewmodel.TransaccionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

private const val TAG = "QRScannerScreen"

@OptIn(ExperimentalGetImage::class)
@Composable
fun QRScannerScreen(
    transaccionViewModel: TransaccionViewModel,
    onScanComplete: () -> Unit = {}
) {
    Log.d(TAG, "QRScannerScreen: Iniciando escáner QR")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val coroutineScope = rememberCoroutineScope()

    // Estados
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var isQRDetected by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isTorchOn by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }
    var scanResult by remember { mutableStateOf<TransaccionPendiente?>(null) }

    // Solicitud de permisos
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    LaunchedEffect(isQRDetected) {
        if (isQRDetected) {
            delay(2000)
            isQRDetected = false
            scanResult?.let {
                onScanComplete()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (hasCameraPermission) {
                AndroidView(
                    factory = { context ->
                        val previewView = PreviewView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        }

                        val preview = Preview.Builder().build()
                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)

                        val imageAnalysis = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                        imageAnalysis.setAnalyzer(
                            Executors.newSingleThreadExecutor()
                        ) { imageProxy ->
                            if (isProcessing) {
                                imageProxy.close()
                                return@setAnalyzer
                            }

                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )

                                val scanner = BarcodeScanning.getClient()
                                scanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        for (barcode in barcodes) {
                                            if (barcode.valueType == Barcode.TYPE_TEXT) {
                                                barcode.rawValue?.let { jsonString ->
                                                    try {
                                                        isProcessing = true
                                                        Log.d(TAG, "QR detectado: $jsonString")

                                                        val transaccion = Gson().fromJson(
                                                            jsonString,
                                                            TransaccionPendiente::class.java
                                                        )

                                                        coroutineScope.launch {
                                                            try {
                                                                transaccionViewModel.marcarProductoComoVendido(transaccion)
                                                                isQRDetected = true
                                                                scanResult = transaccion
                                                                errorMessage = null
                                                            } catch (e: Exception) {
                                                                errorMessage = "Error al procesar la transacción: ${e.message}"
                                                                Log.e(TAG, "Error al procesar transacción", e)
                                                            } finally {
                                                                delay(2000)
                                                                isProcessing = false
                                                            }
                                                        }
                                                    } catch (e: Exception) {
                                                        errorMessage = "QR inválido: No es una transacción"
                                                        Log.e(TAG, "Error parseando QR", e)
                                                        isProcessing = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error escaneando QR", e)
                                        errorMessage = "Error al escanear: ${e.message}"
                                        isProcessing = false
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            } else {
                                imageProxy.close()
                            }
                        }

                        try {
                            val cameraProvider = cameraProviderFuture.get()
                            cameraProvider.unbindAll()
                            val camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview,
                                imageAnalysis
                            )

                            // Habilitar control de linterna
                            if (camera.cameraInfo.hasFlashUnit()) {
                                camera.cameraControl.enableTorch(isTorchOn)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error al vincular la cámara", e)
                            errorMessage = "Error al iniciar la cámara: ${e.message}"
                        }

                        previewView
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { previewView ->
                        // Este bloque se ejecuta cuando los estados cambian
                        try {
                            val cameraProvider = cameraProviderFuture.get()
                            val camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA
                            )

                            // Actualizar estado de la linterna
                            if (camera.cameraInfo.hasFlashUnit()) {
                                camera.cameraControl.enableTorch(isTorchOn)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error al actualizar la cámara", e)
                        }
                    }
                )

                // Marco para guiar al escaneo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .border(
                                width = 2.dp,
                                color = if (isQRDetected) Color.Green else Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                }

                // Notificaciones y mensajes de estado
                AnimatedVisibility(
                    visible = isQRDetected || errorMessage != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 32.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.8f),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                isQRDetected -> Color(0xFF4CAF50)
                                errorMessage != null -> Color(0xFFF44336)
                                else -> MaterialTheme.colorScheme.surface
                            }
                        )
                    ) {
                        Text(
                            text = when {
                                isQRDetected -> "¡QR escaneado con éxito!"
                                errorMessage != null -> errorMessage ?: "Error desconocido"
                                else -> ""
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Controles
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color(0x88000000))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Apunta la cámara al código QR de la transacción",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Botón de linterna
                        Button(
                            onClick = { isTorchOn = !isTorchOn },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isTorchOn) Color.Yellow else Color.DarkGray
                            )
                        ) {
                            Text(if (isTorchOn) "Apagar Linterna" else "Encender Linterna")
                        }
                    }
                }
            } else {
                // UI para cuando no hay permisos
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Se necesita permiso de cámara para escanear códigos QR",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { launcher.launch(Manifest.permission.CAMERA) }
                    ) {
                        Text("Solicitar Permiso")
                    }
                }
            }

            // Indicador de carga
            if (isProcessing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x88000000)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}