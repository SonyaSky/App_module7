package com.hits.myapplication

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import android.widget.ImageView

class ImageFilters(private val activity: MainActivity) {

    private fun inversionFilter(image: Bitmap): Bitmap {
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val newPixel = Color.rgb(
                    255 - r,
                    255 - g,
                    255 - b
                )
                filteredImage.setPixel(x, y, newPixel)
            }
        }
        return filteredImage
    }

    private fun grayscaleFilter(image: Bitmap): Bitmap {
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val gray = ((r + b + g)/3)
                val newPixel = Color.rgb(gray, gray, gray)
                filteredImage.setPixel(x, y, newPixel)
            }
        }
        return filteredImage
    }

    private fun blackAndWhiteFilter(image: Bitmap): Bitmap {
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                if ((r+g+b)/3 > 128)
                {
                    val newPixel = Color.rgb(255,255,255)
                    filteredImage.setPixel(x, y, newPixel)
                }
                else
                {
                    val newPixel = Color.rgb(0,0,0)
                    filteredImage.setPixel(x, y, newPixel)
                }
            }
        }
        return filteredImage
    }

    private fun colorFilter(image: Bitmap, color:String): Bitmap {
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val newPixel = when (color) {
                    "blue" -> Color.rgb(0, 0, b)
                    "green" -> Color.rgb(0, g, 0)
                    "red" -> Color.rgb(r, 0, 0)
                    else -> break
                }
                filteredImage.setPixel(x, y, newPixel)
            }
        }
        return filteredImage
    }

    fun setOnClickListeners() {
        val invertButton = activity.findViewById<Button>(R.id.invert)
        val grayButton = activity.findViewById<Button>(R.id.gray)
        val blackAndWhiteButton = activity.findViewById<Button>(R.id.bnw)
        val blueButton = activity.findViewById<Button>(R.id.blue)
        val greenButton = activity.findViewById<Button>(R.id.green)
        val redButton = activity.findViewById<Button>(R.id.red)
        val imageView = activity.findViewById<ImageView>(R.id.imageView)

        invertButton.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val filteredBitmap = inversionFilter(bitmap)
            imageView.setImageBitmap(filteredBitmap)
        }

        grayButton.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val filteredBitmap = grayscaleFilter(bitmap)
            imageView.setImageBitmap(filteredBitmap)
        }

        blackAndWhiteButton.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val filteredBitmap = blackAndWhiteFilter(bitmap)
            imageView.setImageBitmap(filteredBitmap)
        }

        blueButton.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val filteredBitmap = colorFilter(bitmap, "blue")
            imageView.setImageBitmap(filteredBitmap)
        }

        greenButton.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val filteredBitmap = colorFilter(bitmap, "green")
            imageView.setImageBitmap(filteredBitmap)
        }

        redButton.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val filteredBitmap = colorFilter(bitmap, "red")
            imageView.setImageBitmap(filteredBitmap)
        }
    }
}