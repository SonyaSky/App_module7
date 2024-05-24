package com.example.module7.activity


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.module7.databinding.ActivityDrawingBinding
import java.io.FileNotFoundException
import kotlin.math.log

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

        var brushSize = 10f
        var transparent = 1.0f

        binding.clearButton.setOnClickListener {
            binding.drawingView.clearCanvas()
        }

        binding.colorsBar.black.setOnClickListener {
            binding.drawingView.setBrushColor(Color.BLACK)
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.white.setOnClickListener {
            binding.drawingView.setBrushColor(Color.WHITE)
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.red.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#F44336"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.orange.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#FF9800"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.yellow.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#FFEB3B"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.green.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#72AF4C"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.lightBlue.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#57D0E4"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.blue.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#0000FF"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.purple.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#A834BC"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.colorsBar.pink.setOnClickListener {
            binding.drawingView.setBrushColor(Color.parseColor("#E91E63"))
            binding.drawingView.setRetouchRatio(transparent)
        }

        binding.drawingView.setBrushSize(brushSize)
        binding.drawingView.setRetouchRatio(transparent)

        binding.brushSize.valueTo = 100f
        binding.brushSize.values = listOf(brushSize)
        binding.brushSize.addOnChangeListener { slider, value, fromUser ->
            brushSize = value
            binding.drawingView.setBrushSize(brushSize)
        }

        binding.brushTransparent.valueTo = 100f
        binding.brushTransparent.values = listOf((transparent * 100))
        binding.brushTransparent.addOnChangeListener { slider, value, fromUser ->
            transparent = value / 100f
            binding.drawingView.setRetouchRatio(transparent)
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
