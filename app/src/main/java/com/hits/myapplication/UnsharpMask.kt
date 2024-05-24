package com.hits.myapplication

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.math.min
import kotlin.math.max
import kotlin.math.PI

class UnsharpMask(private val activity: MaskingActivity, private val originalImage: Bitmap) {

    //private val radius = 5
    private fun createGKernel(radius: Int): Array<DoubleArray> {
        val size = radius * 2 + 1
        val sigma = radius.toDouble()
        val kernel = Array(size) { DoubleArray(size) }
        var sum = 0.0
        for (x in -radius..radius) {
            for (y in -radius..radius) {
                val dist = (x * x + y * y).toDouble()
                val value = exp(-dist / (2 * sigma * sigma)) / (2 * PI * sigma * sigma)
                kernel[x + radius][y + radius] = value
                sum += value
            }
        }

        for (x in 0 until size) {
            for (y in 0 until size) {
                kernel[x][y] /= sum
            }
        }

        return kernel
    }


    private fun createMask(image: Bitmap, radius: Int, amount: Double, threshold: Int): Bitmap {
        val result = Bitmap.createBitmap(image.width, image.height, image.config)
        val kernel = createGKernel(radius)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                var red = 0.0
                var green = 0.0
                var blue = 0.0
                for (kx in -radius..radius) {
                    for (ky in -radius..radius) {
                        val ix = x + kx
                        val iy = y + ky
                        if (ix >= 0 && ix < image.width && iy >= 0 && iy < image.height) {
                            val pixel = image.getPixel(ix, iy)
                            val alpha = Color.alpha(pixel)
                            val r = Color.red(pixel)
                            val g = Color.green(pixel)
                            val b = Color.blue(pixel)
                            red += r * kernel[kx + radius][ky + radius] * alpha / 255.0
                            green += g * kernel[kx + radius][ky + radius] * alpha / 255.0
                            blue += b * kernel[kx + radius][ky + radius] * alpha / 255.0
                        }
                    }
                }

                val pixel = image.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b
                val diff = luminance - (0.2126 * red + 0.7152 * green + 0.0722 * blue)
                if (diff > threshold) {
                    val newRed = min(max(r + diff * amount, 0.0), 255.0).toInt()
                    val newGreen = min(max(g + diff * amount, 0.0), 255.0).toInt()
                    val newBlue = min(max(b + diff * amount, 0.0), 255.0).toInt()
                    result.setPixel(x, y, Color.rgb(newRed, newGreen, newBlue))
                } else {
                    result.setPixel(x, y, pixel)
                }
            }
        }
        return result
    }


    fun setOnClickListeners(imageView: AppCompatImageView) {

        val start = activity.findViewById<Button>(R.id.start)
        val amount = activity.findViewById<MySeekBar>(R.id.effectSeekBar)
        val radius = activity.findViewById<MySeekBar>(R.id.radiusSeekBar)
        val threshold = activity.findViewById<MySeekBar>(R.id.thresholdSeekBar)
        val textView = activity.findViewById<TextView>(R.id.textView)


        start.setOnClickListener {
            textView.text = "working"
            val amount1 = amount.progress /100.0
            val radius1 = radius.progress
            val threshold1 = threshold.progress
            val mask = createMask(originalImage, radius1, amount1,threshold1)
            imageView.setImageBitmap(mask)
            textView.text = "done"

        }

    }
}