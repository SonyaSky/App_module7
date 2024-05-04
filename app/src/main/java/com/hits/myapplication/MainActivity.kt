package com.hits.myapplication

import android.os.Bundle
import android.graphics.Color
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import android.widget.Button
import android.widget.ImageView
import com.hits.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }

        val invertButton = findViewById<Button>(R.id.invert)
        val grayButton = findViewById<Button>(R.id.gray)
        val blackAndWhiteButton = findViewById<Button>(R.id.bnw)
        val blueButton = findViewById<Button>(R.id.blue)
        val greenButton = findViewById<Button>(R.id.green)
        val redButton = findViewById<Button>(R.id.red)
        val imageView = findViewById<ImageView>(R.id.imageView)


        fun inversionFilter(image: Bitmap): Bitmap {
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

        fun grayscaleFilter(image: Bitmap): Bitmap {
            val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
            for (x in 0 until image.width) {
                for (y in 0 until image.height) {
                    val pixel = image.getPixel(x, y)
                    val r = Color.red(pixel)
                    val g = Color.green(pixel)
                    val b = Color.blue(pixel)
                    val newPixel = Color.rgb(
                        ((r + b + g)/3).toInt(),
                        ((r + b + g)/3).toInt(),
                        ((r + b + g)/3).toInt()
                    )
                    filteredImage.setPixel(x, y, newPixel)
                }
            }
            return filteredImage
        }

        fun blackAndWhiteFilter(image: Bitmap): Bitmap {
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

        fun colorFilter(image: Bitmap, color:String): Bitmap {
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
                }
            }
            return filteredImage
        }


        invertButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val invertedImage = inversionFilter(image)
            imageView.setImageBitmap(invertedImage)
        }

        grayButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val grayscaleImage = grayscaleFilter(image)
            imageView.setImageBitmap(grayscaleImage)
        }

        blackAndWhiteButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val blackAndWhiteImage = blackAndWhiteFilter(image)
            imageView.setImageBitmap(blackAndWhiteImage)
        }

        blueButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val blueImage = colorFilter(image, "blue")
            imageView.setImageBitmap(blueImage)
        }

        greenButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val greenImage = colorFilter(image, "green")
            imageView.setImageBitmap(greenImage)
        }

        redButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val redImage = colorFilter(image, "red")
            imageView.setImageBitmap(redImage)
        }




    }

}

