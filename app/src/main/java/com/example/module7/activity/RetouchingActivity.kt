package com.example.module7.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import androidx.core.content.FileProvider
import com.example.module7.databinding.ActivityRetouchingBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class RetouchingActivity : ComponentActivity() {
    private lateinit var binding : ActivityRetouchingBinding
    private lateinit var uri : Uri
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetouchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriString = intent.getStringExtra("image_uri")
        uriString?.let {
            uri = Uri.parse(it)
            bitmap = getBitmapFromUri(uri)
            binding.imageView.setImageBitmap(bitmap)
        }


        // Default values for brush size and retouch ratio
        val defaultBrushSize = 10f
        val defaultRetouchRatio = 1.0f

        binding.drawingView.setBrushSize(defaultBrushSize)
        binding.drawingView.setRetouchRatio(defaultRetouchRatio)

        binding.clearButton.setOnClickListener {
            binding.drawingView.clearCanvas()
        }

        binding.imageView.post {
            val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
            binding.drawingView.setImageBitmap(bitmap)
        }

        // Set up brush size seek bar
        binding.brushSizeSeekBar.max = 100
        binding.brushSizeSeekBar.progress = defaultBrushSize.toInt()
        binding.brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newSize = progress.toFloat()
                binding.drawingView.setBrushSize(newSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up retouch ratio seek bar
        binding.brushRatioSeekBar.max = 100
        binding.brushRatioSeekBar.progress = (defaultRetouchRatio * 100).toInt()
        binding.brushRatioSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newRatio = progress / 100f
                binding.drawingView.setRetouchRatio(newRatio)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
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

    private fun saveBitmapAndGetUri(bitmap: Bitmap): Uri? {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_image.png")
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            return FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}

