package com.example.module7.components

import android.graphics.Bitmap
import android.graphics.Color

import kotlinx.coroutines.*

class ResizeImage {

    fun resize(bitmap: Bitmap, coefficient: Float): Bitmap = runBlocking {
        val width = (bitmap.width * coefficient).toInt()
        val height = (bitmap.height * coefficient).toInt()
        val newBitmap = Bitmap.createBitmap(width, height, bitmap.config)

        val chunks = 4
        val chunkHeight = height / chunks

        val jobs = List(chunks) { chunk ->
            async(Dispatchers.Default) {
                for (x in 0 until width) {
                    for (y in chunk * chunkHeight until (chunk + 1) * chunkHeight) {
                        val xCoefficient = x.toDouble() / width * (bitmap.width - 1)
                        val yCoefficient = y.toDouble() / height * (bitmap.height - 1)

                        val x1 = xCoefficient.toInt()
                        val y1 = yCoefficient.toInt()
                        val x2 = if (x1 == bitmap.width - 1) x1 else x1 + 1
                        val y2 = if (y1 == bitmap.height - 1) y1 else y1 + 1

                        val color1 = bitmap.getPixel(x1, y1)
                        val color2 = bitmap.getPixel(x2, y1)
                        val color3 = bitmap.getPixel(x1, y2)
                        val color4 = bitmap.getPixel(x2, y2)

                        val red = interpolateColor(
                            xCoefficient, yCoefficient, x1, y1, x2, y2,
                            Color.red(color1), Color.red(color2), Color.red(color3), Color.red(color4)
                        )
                        val green = interpolateColor(
                            xCoefficient, yCoefficient, x1, y1, x2, y2,
                            Color.green(color1), Color.green(color2), Color.green(color3), Color.green(color4)
                        )
                        val blue = interpolateColor(
                            xCoefficient, yCoefficient, x1, y1, x2, y2,
                            Color.blue(color1), Color.blue(color2), Color.blue(color3), Color.blue(color4)
                        )
                        val alpha = interpolateColor(
                            xCoefficient, yCoefficient, x1, y1, x2, y2,
                            Color.alpha(color1), Color.alpha(color2), Color.alpha(color3), Color.alpha(color4)
                        )

                        newBitmap.setPixel(x, y, Color.argb(alpha, red, green, blue))
                    }
                }
            }
        }

        jobs.awaitAll()
        return@runBlocking newBitmap
    }

    fun interpolateColor(
        xCoefficient: Double, yCoefficient: Double,
        x1: Int, y1: Int, x2: Int, y2: Int,
        c1: Int, c2: Int, c3: Int, c4: Int
    ): Int {
        val c1Weight = (x2 - xCoefficient) * (y2 - yCoefficient)
        val c2Weight = (xCoefficient - x1) * (y2 - yCoefficient)
        val c3Weight = (x2 - xCoefficient) * (yCoefficient - y1)
        val c4Weight = (xCoefficient - x1) * (yCoefficient - y1)

        return (c1 * c1Weight + c2 * c2Weight + c3 * c3Weight + c4 * c4Weight).toInt()
    }
}