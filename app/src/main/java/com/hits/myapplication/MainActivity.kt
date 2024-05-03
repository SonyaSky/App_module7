package com.hits.myapplication

import android.os.Bundle
import android.graphics.Color
import android.graphics.Bitmap
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
        val imageView = findViewById<ImageView>(R.id.imageView)

        invertButton.setOnClickListener {
            val image = (imageView.drawable as BitmapDrawable).bitmap
            val invertedImage = inversionFilter(image)
            imageView.setImageBitmap(invertedImage)
        }

        fun inversionFilter(image: Bitmap): Bitmap {
            val filteredImage = Bitmap.createBitmap(image.width, image.height, image.config)
            for (x in 0 until image.width) {
                for (y in 0 until image.height) {
                    val pixel = image.getPixel(x, y)
                    val r = Color.red(pixel)
                    val g = Color.green(pixel)
                    val b = Color.blue(pixel)
                    val newPixel = Color.rgb(
                        255 - r.toInt(),
                        255 - g.toInt(),
                        255 - b.toInt()
                    )
                    filteredImage.setPixel(x, y, newPixel)
                }
            }
            return filteredImage
        }
    }



}

