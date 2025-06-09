package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import androidx.navigation.NavHostController
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.presentation.viewmodel.MensajeViewModel
import com.nextmacrosystem.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.nextmacrosystem.reciclapp.util.TipoDeUsuario.VENDEDOR
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.UUID

private const val TAG = "QRGeneratorDialog"

@SuppressLint("NewApi")
@Composable
fun QRGeneratorScreen(
    transaccionViewModel: TransaccionViewModel,
    mensajeViewModel: MensajeViewModel,
    mainNavHostController: NavHostController
) {
    val productosSeleccionados = transaccionViewModel.productosSeleccionados.collectAsState()
    val usuarioContactado = transaccionViewModel.usuarioContactado.collectAsState()
    val puntosParaComprador = transaccionViewModel.puntosParaComprador.collectAsState()
    val puntosParaAmbosUsuarios = transaccionViewModel.puntosParaAmbosUsuarios.collectAsState()
    val idsProductosSeleccionados = transaccionViewModel.idsProductosSeleccionados.collectAsState()
    var isQRLoading by remember { mutableStateOf(true) }
    var isButtonLoading by remember { mutableStateOf(false) }


    val usuarioContactadoIsVendedor =
        usuarioContactado.value?.tipoDeUsuario == VENDEDOR
    val idVendedor = if (usuarioContactadoIsVendedor) usuarioContactado.value?.idUsuario ?: ""
    else transaccionViewModel.myUser.value?.idUsuario ?: ""

    val idComprador =
        if (usuarioContactadoIsVendedor) transaccionViewModel.myUser.value?.idUsuario ?: ""
        else usuarioContactado.value?.idUsuario ?: ""

    val transaccionInfo = remember {
        JsonObject().apply {
            addProperty("idTransaccion", UUID.randomUUID().toString())
            addProperty("idsProductos", idsProductosSeleccionados.value)
            addProperty("idVendedor", idVendedor)
            addProperty("idComprador", idComprador)
            addProperty("fecha", LocalDateTime.now().toString())
            addProperty("puntosComprador", puntosParaComprador.value)
            addProperty("puntosAmbosUsuarios", puntosParaAmbosUsuarios.value)
        }.toString()
    }

    // Simular tiempo de carga para generar el QR
    LaunchedEffect(Unit) {
        delay(1500) // Simular carga del QR por 1.5 segundos
        isQRLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Transacción",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Resumen de productos
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    productosSeleccionados.value.forEach { producto ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = producto.nombreProducto,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${producto.precio} ${producto.monedaDeCompra}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // QR Code Card with loading animation
            Card(
                modifier = Modifier
                    .size(280.dp)
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isQRLoading) {
                        // Animación mientras se genera el QR
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                        }
                    } else {
                        QRCode(
                            content = transaccionInfo,
                            modifier = Modifier.fillMaxSize(),
                            onQRGenerated = { /* No need to do anything here */ })
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Muestra este código QR al ${usuarioContactado.value?.tipoDeUsuario?: "otro usuario"} para validar la transacción y que ambos reciban sus puntos",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (!isButtonLoading && !isQRLoading) {
                            isButtonLoading = true

                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isButtonLoading && !isQRLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isButtonLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Guardar y continuar con la transacción",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    // Gestionar la barra de progreso del botón cuando se está cargando
    LaunchedEffect(isButtonLoading) {
        if (isButtonLoading) {

            delay(1000)

            mensajeViewModel.setTransaccionPendiente(
                TransaccionPendiente(
                    idTransaccion = JsonParser.parseString(transaccionInfo).asJsonObject["idTransaccion"].asString,
                    idsProductos = idsProductosSeleccionados.value,
                    idVendedor = idVendedor,
                    idComprador = idComprador,
                    fechaCreacion = LocalDateTime.now().toString(),
                    codigoQR = transaccionInfo,
                    puntosComprador = puntosParaComprador.value,
                    puntosAmbosUsuarios = puntosParaAmbosUsuarios.value
                )
            )

            mainNavHostController.navigate("SendingProductsScreen") {
                popUpTo("SendingProductsScreen") { inclusive = true }
            }
        }
    }
}

@Composable
fun QRCode(
    content: String, modifier: Modifier = Modifier, onQRGenerated: (Bitmap) -> Unit
) {
    val writer = QRCodeWriter()
    val hints = HashMap<EncodeHintType, Any>()
    hints[EncodeHintType.MARGIN] = 0
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.Q

    val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bitmap = createBitmap(width, height)

    val qrColor = android.graphics.Color.BLACK
    val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap[x, y] = if (bitMatrix[x, y]) qrColor else backgroundColor
        }
    }

    LaunchedEffect(bitmap) {
        onQRGenerated(bitmap)
    }

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Código QR de transacción",
        modifier = modifier.padding(8.dp),
        contentScale = ContentScale.Fit
    )
}