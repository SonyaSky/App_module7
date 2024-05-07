package com.example.module7

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.module7.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val changeImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val imgUri = data?.data
            val rotatedBitmap = imgUri?.let { rotateImage(it) }
            rotatedBitmap?.let { rotatedImage ->
                binding.selectedImage.setImageBitmap(rotatedImage)
            }
        }
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pickImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        binding.rotateImage.setOnClickListener {
            val currentBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            currentBitmap?.let { bitmap ->
                val rotatedBitmap = rotateBitmap(bitmap, 90f)
                binding.selectedImage.setImageBitmap(rotatedBitmap)
            }
        }

        binding.saveImage.setOnClickListener {
            val rotatedBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            rotatedBitmap?.let { bitmap ->
                saveBitmap(bitmap)
            }
        }
    }

    private fun rotateImage(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()) ?: return
        contentResolver.openOutputStream(imageUri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }
}