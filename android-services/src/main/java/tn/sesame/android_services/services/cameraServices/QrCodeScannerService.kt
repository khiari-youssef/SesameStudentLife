package tn.sesame.android_services.services.cameraServices

import android.graphics.Rect
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


internal class QRCodeImageAnalyzer(
    private val onResult: (data: String?) -> Unit,
    private val onScanProgress : (rect : Rect?)->Unit,
    private val barCodeValidator: (barcode: Barcode) -> Boolean,
    private val onBarCodeScanError: () -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .setZoomSuggestionOptions(
             ZoomSuggestionOptions.Builder {
                 true
             }
           .build())
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        onScanProgress(bounds)
                        if (barCodeValidator(barcode)) {
                            onResult(barcode.rawValue)
                        }
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    onBarCodeScanError()
                }
                .addOnCanceledListener {
                    imageProxy.close()
                }
                .addOnCompleteListener{
                    imageProxy.close()
                }

        }
    }
}
