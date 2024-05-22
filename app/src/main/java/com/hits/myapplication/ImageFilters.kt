package com.hits.myapplication

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class ImageFilters(private val activity: MainActivity, private val originalImage: Bitmap) {

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

    private fun sepiaFilter(image: Bitmap): Bitmap {
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val newPixel = Color.rgb(
                    min(255,(r * 0.393 + g * 0.769 + b * 0.189).toInt()),
                    min(255, (r * 0.349 + g * 0.686 + b * 0.168).toInt()),
                    min(255,(r * 0.272 + g * 0.534 + b * 0.131).toInt())
                )
                filteredImage.setPixel(x, y, newPixel)
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

    private fun tintFilter(image: Bitmap, color:String): Bitmap {
        val tint = when (color) {
            "pink" -> Color.rgb(240, 57, 171)
            "orange" -> Color.rgb(232, 76, 36)
            "yellow" -> Color.rgb(252, 242, 56)
            "purple" -> Color.rgb(176, 38, 255)
            "green" -> Color.rgb(48, 194, 100)
            else -> Color.rgb(0, 0, 0)
        }
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = (Color.red(pixel) + Color.red(tint)) / 2
                val g = (Color.green(pixel) + Color.green(tint)) / 2
                val b = (Color.blue(pixel) + Color.blue(tint)) / 2
                filteredImage.setPixel(x, y, Color.rgb(r.coerceAtMost(255), g.coerceAtMost(255), b.coerceAtMost(255)))
            }
        }
        return filteredImage
    }

    private fun lightFilter(image: Bitmap, type: String): Bitmap {
        val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
        val factor:Double
        if (type == "light")
            factor = 1.5
        else
            factor = 0.5
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixel = image.getPixel(x, y)
                val r = (Color.red(pixel) * factor).toInt().coerceAtMost(255)
                val g = (Color.green(pixel) * factor).toInt().coerceAtMost(255)
                val b = (Color.blue(pixel) * factor).toInt().coerceAtMost(255)
                filteredImage.setPixel(x, y, Color.rgb(r, g, b))
            }
        }
        return filteredImage
    }

    private fun createThumbnail(image: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    fun setOnClickListeners(imageView: AppCompatImageView) {

//        val images = Array<Bitmap>(14) { index ->
//            when (index) {
//                0 -> inversionFilter(originalImage)
//                1 -> grayscaleFilter(originalImage)
//                2 -> blackAndWhiteFilter(originalImage)
//                3 -> lightFilter(originalImage, "dark")
//                4 -> lightFilter(originalImage, "light")
//                5 -> sepiaFilter(originalImage)
//                6 -> tintFilter(originalImage, "yellow")
//                7 -> tintFilter(originalImage, "green")
//                8 -> tintFilter(originalImage, "orange")
//                9 -> tintFilter(originalImage, "pink")
//                10 -> tintFilter(originalImage, "purple")
//                11 -> colorFilter(originalImage, "blue")
//                12 -> colorFilter(originalImage, "green")
//                else -> colorFilter(originalImage, "red")
//            }
//        }

        val invertButton = activity.findViewById<ImageButton>(R.id.invert)
        val grayButton = activity.findViewById<ImageButton>(R.id.gray)
        val blackAndWhiteButton = activity.findViewById<ImageButton>(R.id.bnw)
        val darkButton = activity.findViewById<ImageButton>(R.id.dark)
        val lightButton = activity.findViewById<ImageButton>(R.id.light)
        val sepiaButton = activity.findViewById<ImageButton>(R.id.sepia)
        val sageButton = activity.findViewById<ImageButton>(R.id.sage)
        val orangeButton = activity.findViewById<ImageButton>(R.id.orange)
        val pinkButton = activity.findViewById<ImageButton>(R.id.pink)
        val yellowButton = activity.findViewById<ImageButton>(R.id.yellow)
        val purpleButton = activity.findViewById<ImageButton>(R.id.purple)
        val blueButton = activity.findViewById<ImageButton>(R.id.blue)
        val greenButton = activity.findViewById<ImageButton>(R.id.green)
        val redButton = activity.findViewById<ImageButton>(R.id.red)

        val thumbnailWidth = 200
        val thumbnailHeight = 200

        val smallImage = createThumbnail(originalImage, thumbnailWidth, thumbnailHeight)


        val invertThumbnail = inversionFilter(smallImage)
        val grayThumbnail = grayscaleFilter(smallImage)
        val blackAndWhiteThumbnail = blackAndWhiteFilter(smallImage)
        val darkThumbnail = lightFilter(smallImage, "dark")
        val lightThumbnail = lightFilter(smallImage, "light")
        val sepiaThumbnail = sepiaFilter(smallImage)
        val yellowThumbnail = tintFilter(smallImage, "yellow")
        val sageThumbnail = tintFilter(smallImage, "green")
        val orangeThumbnail = tintFilter(smallImage, "orange")
        val pinkThumbnail = tintFilter(smallImage, "pink")
        val purpleThumbnail = tintFilter(smallImage, "purple")
        val blueThumbnail = colorFilter(smallImage, "blue")
        val greenThumbnail = colorFilter(smallImage, "green")
        val redThumbnail = colorFilter(smallImage, "red")

        invertButton.setImageBitmap(invertThumbnail)
        grayButton.setImageBitmap(grayThumbnail)
        blackAndWhiteButton.setImageBitmap(blackAndWhiteThumbnail)
        darkButton.setImageBitmap(darkThumbnail)
        lightButton.setImageBitmap(lightThumbnail)
        sepiaButton.setImageBitmap(sepiaThumbnail)
        orangeButton.setImageBitmap(orangeThumbnail)
        sageButton.setImageBitmap(sageThumbnail)
        pinkButton.setImageBitmap(pinkThumbnail)
        yellowButton.setImageBitmap(yellowThumbnail)
        purpleButton.setImageBitmap(purpleThumbnail)
        blueButton.setImageBitmap(blueThumbnail)
        greenButton.setImageBitmap(greenThumbnail)
        redButton.setImageBitmap(redThumbnail)


        invertButton.setOnClickListener {
            val filteredImage = inversionFilter(originalImage)
            imageView.setImageBitmap(filteredImage)
        }

        grayButton.setOnClickListener {
            val filteredImage = grayscaleFilter(originalImage)
            imageView.setImageBitmap(filteredImage)
        }

        blackAndWhiteButton.setOnClickListener {
            val filteredImage = blackAndWhiteFilter(originalImage)
            imageView.setImageBitmap(filteredImage)
        }

        darkButton.setOnClickListener {
            val filteredImage = lightFilter(originalImage, "dark")
            imageView.setImageBitmap(filteredImage)
        }

        lightButton.setOnClickListener {
            val filteredImage = lightFilter(originalImage, "light")
            imageView.setImageBitmap(filteredImage)
        }

        sepiaButton.setOnClickListener {
            val filteredImage = sepiaFilter(originalImage)
            imageView.setImageBitmap(filteredImage)
        }

        yellowButton.setOnClickListener {
            val filteredImage = tintFilter(originalImage, "yellow")
            imageView.setImageBitmap(filteredImage)
        }

        sageButton.setOnClickListener {
            val filteredImage = tintFilter(originalImage, "green")
            imageView.setImageBitmap(filteredImage)
        }

        orangeButton.setOnClickListener {
            val filteredImage = tintFilter(originalImage, "orange")
            imageView.setImageBitmap(filteredImage)
        }

        pinkButton.setOnClickListener {
            val filteredImage = tintFilter(originalImage, "pink")
            imageView.setImageBitmap(filteredImage)
        }

        purpleButton.setOnClickListener {
            val filteredImage = tintFilter(originalImage, "purple")
            imageView.setImageBitmap(filteredImage)
        }

        blueButton.setOnClickListener {
            val filteredImage = colorFilter(originalImage, "blue")
            imageView.setImageBitmap(filteredImage)
        }

        greenButton.setOnClickListener {
            val filteredImage = colorFilter(originalImage, "green")
            imageView.setImageBitmap(filteredImage)
        }

        redButton.setOnClickListener {
            val filteredImage = colorFilter(originalImage, "red")
            imageView.setImageBitmap(filteredImage)
        }

    }
}