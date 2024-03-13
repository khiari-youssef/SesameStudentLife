package tn.sesame.android_services.ui

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions


@Composable
internal fun ImageDataExtraction(
    modifier : Modifier
) {
    val localModel = LocalModel.Builder()
        .setAssetFilePath("model.tflite")
        .build()

}


private class YourImageAnalyzer(
  private  val localModel : LocalModel
) : ImageAnalysis.Analyzer {

    val customImageLabelerOptions = CustomImageLabelerOptions.Builder(localModel)
        .setConfidenceThreshold(0.5f)
        .setMaxResultCount(5)
        .build()
    val labeler = ImageLabeling.getClient(customImageLabelerOptions)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        val index = label.index
                    }
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }
        }
    }
}