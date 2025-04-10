package com.example.reciclapp.presentation.ui.menu.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
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
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.example.reciclapp.util.TipoDeUsuario
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.time.LocalDateTime
import java.util.UUID

private const val TAG = "QRGeneratorDialog"

@SuppressLint("NewApi")
@Composable
fun QRGeneratorScreen(
    transaccionViewModel: TransaccionViewModel, mainNavHostController: NavHostController
) {

    var showSuccessDialog by remember { mutableStateOf(false) }

    val productosSeleccionados = transaccionViewModel.productosSeleccionados.collectAsState()
    val usuarioContactado = transaccionViewModel.usuarioContactado.collectAsState()

    val puntosParaComprador = transaccionViewModel.puntosParaComprador.collectAsState()

    val puntosParaAmbosUsuarios = transaccionViewModel.puntosParaAmbosUsuarios.collectAsState()

    val usuarioContactadoIsVendedor =
        usuarioContactado.value?.tipoDeUsuario == TipoDeUsuario.VENDEDOR

    val idsProductosSeleccionados = transaccionViewModel.idsProductosSeleccionados.collectAsState()

    val idVendedor = if (usuarioContactadoIsVendedor) usuarioContactado.value?.idUsuario
        ?: "" else transaccionViewModel.myUser.value?.idUsuario ?: ""
    val idComprador =
        if (usuarioContactadoIsVendedor) transaccionViewModel.myUser.value?.idUsuario
            ?: "" else usuarioContactado.value?.idUsuario ?: ""

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Text(
            text = "Código QR de Transacción",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = productosSeleccionados.value.joinToString(separator = "\n") {
                "${it.nombreProducto}: ${it.precio}${it.monedaDeCompra}"
            },
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            val qrBitmap = remember { mutableStateOf<Bitmap?>(null) }

            QRCode(
                content = transaccionInfo,
                modifier = Modifier.fillMaxSize(),
                onQRGenerated = { bitmap ->
                    qrBitmap.value = bitmap
                }
            )
        }

        Text(
            text = "Muestra este código QR al ${if (usuarioContactadoIsVendedor) "vendedor" else "comprador"} para validar la transacción y que ambos puedan recibir sus puntos",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                val transaccion = TransaccionPendiente(
                    idTransaccion = JsonParser.parseString(transaccionInfo)
                        .asJsonObject["idTransaccion"].asString,
                    idsProductos = idsProductosSeleccionados.value,
                    idVendedor = idVendedor,
                    idComprador = idComprador,
                    fechaCreacion = LocalDateTime.now().toString(),
                    codigoQR = transaccionInfo,
                    puntosComprador = puntosParaComprador.value,
                    puntosAmbosUsuarios = puntosParaAmbosUsuarios.value
                )

                Log.d(TAG, "Transaccion: $transaccion")

                    transaccionViewModel.crearTransaccionPendiente(transaccion)
                    showSuccessDialog = true

                transaccionViewModel.enviarOfertaAComprador()


                Log.d(TAG, "IsSuccessful: $showSuccessDialog")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                "Guardar QR y continuar contactando",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        TextButton(onClick = { mainNavHostController.popBackStack() }) {
            Text("Cancelar")
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Transacción guardada") },
            text = { Text("La transacción ha sido guardada exitosamente.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                    }
                ) {
                    Text("Continuar")
                }
            }
        )
    }
}

@Composable
fun QRCode(
    content: String,
    modifier: Modifier = Modifier,
    onQRGenerated: (Bitmap) -> Unit
) {
    val writer = QRCodeWriter()
    val hints = HashMap<EncodeHintType, Any>()
    hints[EncodeHintType.MARGIN] = 1
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

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Código QR de transacción",
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentScale = ContentScale.Fit
        )
    }
}