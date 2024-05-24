package com.example.module7.activity


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.module7.databinding.ActivityDrawingBinding
import java.io.FileNotFoundException

class DrawingActivity : ComponentActivity() {

    private lateinit var binding: ActivityDrawingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriString = intent.getStringExtra("image_uri")
        uriString?.let {
            val uri = Uri.parse(it)
            val bitmap = getBitmapFromUri(uri)
            binding.imageView.setImageBitmap(bitmap)
        }

        val defaultBrushSize = 10f
        val defaultRetouchRatio = 1.0f

        binding.clearButton.setOnClickListener {
            binding.drawingView.clearCanvas()
        }

        binding.colorsBar.black.setOnClickListener {
            binding.drawingView.setBrushColor(Color.BLACK)
        }

        binding.colorsBar.white.setOnClickListener {
            binding.drawingView.setBrushColor(Color.WHITE)
        }

        binding.colorsBar.red.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#F44336"))
        }

        binding.colorsBar.orange.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#FF9800"))
        }

        binding.colorsBar.yellow.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#FFEB3B"))
        }

        binding.colorsBar.green.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("@color/green"))
        }

        binding.colorsBar.lightBlue.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("@color/light_blue"))
        }

        binding.colorsBar.blue.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("@color/blue"))
        }

        binding.colorsBar.purple.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("@color/purple"))
        }

        binding.colorsBar.pink.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("@color/pink"))
        }

        binding.drawingView.setBrushSize(defaultBrushSize)
        binding.drawingView.setRetouchRatio(defaultRetouchRatio)

        binding.brushSize.valueTo = 100f
        binding.brushSize.values = listOf(defaultBrushSize)
        binding.brushSize.addOnChangeListener { slider, value, fromUser ->
            binding.drawingView.setBrushSize(value.toFloat())
        }

        binding.brushRatio.valueTo = 100f
        binding.brushRatio.values = listOf((defaultRetouchRatio * 100))
        binding.brushRatio.addOnChangeListener { slider, value, fromUser ->
            binding.drawingView.setRetouchRatio(value / 100f)
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}
