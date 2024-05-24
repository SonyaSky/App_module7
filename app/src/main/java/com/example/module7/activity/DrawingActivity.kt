package com.example.module7.activity


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import com.example.module7.databinding.ActivityDrawingBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.log

class DrawingActivity : ComponentActivity() {

    private lateinit var binding: ActivityDrawingBinding
    private lateinit var uri : Uri
    private var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriString = intent.getStringExtra("image_uri")
        uriString?.let {
            uri = Uri.parse(it)
            bitmap = getBitmapFromUri(uri)
            binding.imageView.setImageBitmap(bitmap)

            val layoutParams = binding.drawingView.layoutParams
            layoutParams.width = bitmap!!.width
            layoutParams.height = bitmap!!.height
            binding.drawingView.layoutParams = layoutParams
        }


        var brushSize = 10f
        var transparent = 1.0f

        binding.clearButton.setOnClickListener {
            binding.drawingView.clearCanvas()
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
                val uri: Uri? = saveBitmapAndGetUri(mergeBitmaps(bitmap!!))
                uri?.let {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("image_uri", it.toString())
                    startActivity(intent)
                }
            }
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

    fun mergeBitmaps(mainBitmap : Bitmap): Bitmap {
        val resultBitmap = Bitmap.createBitmap(mainBitmap.width, mainBitmap.height, mainBitmap.config)
        val canvas = Canvas(resultBitmap)

        canvas.drawBitmap(mainBitmap, 0f, 0f, null)

        canvas.drawBitmap(binding.drawingView.drawToBitmap(), 0f, 0f, null)

        return resultBitmap
    }
}
