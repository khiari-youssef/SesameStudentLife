package tn.sesame.android_services.ui

import android.content.Context
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.common.Barcode
import tn.sesame.android_services.services.cameraServices.QRCodeImageAnalyzer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    onQrCodeCaptured: (data : String)->Unit
) {
    val context = LocalContext.current

    suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(this))
            }
        }


    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder()
        .setTargetResolution(Size(150,150))
        .build()
    val previewView = remember {
        PreviewView(context)
    }
    val imageAnalysis = ImageAnalysis.Builder()
        .setTargetResolution(Size(150,150))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
    val isQrCode : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val boundingBox : MutableState<android.graphics.Rect?> = remember {
        mutableStateOf(null)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    val qrCodAnalyser =  QRCodeImageAnalyzer(
        onBarCodeScanError = {
            isQrCode.value = false
        },
        onResult = {
            isQrCode.value = true
            if (it != null) {
                onQrCodeCaptured(it)
            }
        },
        barCodeValidator = {
            it.valueType == Barcode.TYPE_TEXT
        },
        onScanProgress = {
            isQrCode.value = false
            boundingBox.value = it
        }
    )
    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context),qrCodAnalyser)
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, imageAnalysis,preview)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
        AndroidView(
            modifier = modifier,
            factory = { previewView }
        )
}