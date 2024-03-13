package tn.sesame.android_services.services

import java.io.File

data class ImageDataExtractorConfiguration(
  val confidenceThreshold : Float,
  val setMaxResultCount : Int
)

interface ImageDataExtractorService {

    fun setup(
        localModelFile : File,
        config : ImageDataExtractorConfiguration = ImageDataExtractorConfiguration(
            confidenceThreshold = 0.5f,
            setMaxResultCount = 5
        )
    )
}