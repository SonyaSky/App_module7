package com.example.module7.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
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

        var brushSize = 10f
        var transparent = 1.0f

        binding.drawingView.setBrushSize(brushSize)
        binding.drawingView.setRetouchRatio(transparent)

        binding.clearButton.setOnClickListener {
            binding.drawingView.clearCanvas()
            binding.drawingView.setImageBitmap(bitmap!!)
        }

        binding.imageView.post {
            binding.drawingView.setImageBitmap(bitmap!!)
        }

        // Set up brush size seek bar
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
        binding.backBtn.setOnClickListener {
            uri?.let {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("image_uri", it.toString())
                startActivity(intent)
            }
        }

        binding.approveBtn.setOnClickListener {
            if (bitmap != null) {
                val uri: Uri? = saveBitmapAndGetUri(binding.drawingView.drawToBitmap())
                uri?.let {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("image_uri", it.toString())
                    startActivity(intent)
                }
            }
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

