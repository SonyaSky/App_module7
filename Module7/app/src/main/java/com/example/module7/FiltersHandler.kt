package com.example.module7

import android.graphics.Bitmap

interface FiltersHandler {

    fun sendToRotateImage(angle: Float)

    fun sendToInversionFilter()

    fun sendToGrayscaleFilter()

    fun sendToBNWFilter()

    fun sendToLightFilter(type: String)

    fun sendToSepiaFilter()

    fun sendToTintFilter(color: String)

    fun sendToColorFilter(color: String)

    fun takePhotosForButtons() : List<Bitmap?>?


<<<<<<< Updated upstream
=======
    fun sendToMasking(radius: Int, effect: Double, threshold: Int)
>>>>>>> Stashed changes
}