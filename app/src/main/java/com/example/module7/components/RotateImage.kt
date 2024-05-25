package com.example.module7.components

import android.graphics.Bitmap
import android.graphics.Matrix

class RotateImage {
    fun rotateBitmap(bitmap: Bitmap, angle: Float, width : Float, height : Float): Bitmap {
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

    fun rotate90Degrees(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}

/*fun rotateBitmap(bitmap: Bitmap, angle: Float, width: Float, height: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)


        val radians = Math.toRadians(angle.toDouble() % 90)
        val k = (1 + ((cos(radians) + (height/width)* sin(radians))*(sin(radians) + (width/height)* cos(radians)))).toFloat()

        matrix.postScale(k, k)

        // Применяем поворот и масштабирование
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        // Создаем новое изображение, в которое поместим повернутый bitmap
        val filledBitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(filledBitmap)

        // Рассчитываем смещение для центрирования
        val offsetX = (width - rotatedBitmap.width) / 2
        val offsetY = (height - rotatedBitmap.height) / 2

        // Находим центр изначального изображения
        val centerX = width / 2
        val centerY = height / 2

        // Рисуем повернутое изображение с учетом смещения
        canvas.drawBitmap(rotatedBitmap, offsetX, offsetY, null)

        return filledBitmap
    } */