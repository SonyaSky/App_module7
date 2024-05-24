package com.example.module7.components

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log

class RotateImage {
    public fun rotateBitmap(bitmap: Bitmap, angle: Float, width : Float, height : Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)

        val scaleFactor = minOf(
            width / bitmap.width,
            height / bitmap.height
        )
        matrix.postScale(scaleFactor, scaleFactor)
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        return rotatedBitmap
    }
}