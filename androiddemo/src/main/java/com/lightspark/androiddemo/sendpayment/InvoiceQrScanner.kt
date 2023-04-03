package com.lightspark.androiddemo.sendpayment

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun InvoiceQrScanner(
    modifier: Modifier = Modifier,
    onInvoiceScanned: ((String) -> Unit)? = null,
    onManualEntryRequest: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }
    val barcodeOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()
    val barcodeScanner = remember { BarcodeScanning.getClient(barcodeOptions) }
    val cameraController = remember { LifecycleCameraController(context) }

    LaunchedEffect(CameraSelector.LENS_FACING_BACK) {
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(context),
            ) { result: MlKitAnalyzer.Result? ->
                val barcodeResults = result?.getValue(barcodeScanner)
                if ((barcodeResults == null) ||
                    (barcodeResults.size == 0) ||
                    (barcodeResults.first() == null)
                ) {
                    previewView.overlay.clear()
                    previewView.setOnTouchListener { _, _ -> false } // no-op
                    return@MlKitAnalyzer
                }

                val barcode = barcodeResults.first()!!
                barcode.rawValue?.let { onInvoiceScanned?.invoke(it) }
                // TODO: Maybe show the QR code overlay?
            },
        )

        cameraController.bindToLifecycle(lifecycleOwner)
        previewView.controller = cameraController
    }

    DisposableEffect(Unit) {
        onDispose {
            previewView.controller = null
            cameraController.unbind()
        }
    }

    val viewFinderSize = with(LocalDensity.current) { 300.dp.toPx() }
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Canvas(modifier = Modifier.fillMaxSize()) {
            val finderPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        Rect(
                            center = Offset(size.width / 2f, size.height / 2f),
                            viewFinderSize / 2f,
                        ),
                        viewFinderSize * 0.2f,
                        viewFinderSize * 0.2f,
                    ),
                )
            }
            clipPath(finderPath, clipOp = ClipOp.Difference) {
                drawRect(SolidColor(Color.Black.copy(alpha = 0.6f)))
            }
            drawPath(
                finderPath,
                Color.White,
                style = Stroke(
                    3f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                ),
            )
        }
        Text(
            text = "Scan a BTC or Lightning QR code",
            color = Color.White,
            modifier = Modifier
                .width(300.dp)
                .offset(y = (-180).dp),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = { onManualEntryRequest?.invoke() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp),
        ) {
            Text(text = "Enter an address")
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this),)
        }
    }
