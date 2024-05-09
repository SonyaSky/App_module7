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
import android.widget.Toast
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

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pickImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        binding.takePhotoButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

        }

        binding.rotateImage.setOnClickListener {
            val currentBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            currentBitmap?.let { bitmap ->
                val rotatedBitmap = rotateBitmap(bitmap, 90f)
                binding.selectedImage.setImageBitmap(rotatedBitmap)
            }
        }

        binding.scaleImage.setOnClickListener {
            val currentBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            currentBitmap?.let { bitmap ->
                val scaleFactor = binding.scaleFactorEditText.text.toString().toFloatOrNull()
                scaleFactor?.let {
                    val scaledBitmap = scaleBitmap(bitmap, it)
                    binding.selectedImage.setImageBitmap(scaledBitmap)
                } ?: showToast("Invalid scale factor")
            } ?: showToast("No image selected")
        }

        binding.saveImage.setOnClickListener {
            val rotatedBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            rotatedBitmap?.let { bitmap ->
                saveBitmap(bitmap)
            }
            showToast("Image saved to gallery")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.selectedImage.setImageBitmap(imageBitmap)
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

    private fun scaleBitmap(bitmap: Bitmap, scaleFactor: Float): Bitmap {
        val width = (bitmap.width * scaleFactor).toInt()
        val height = (bitmap.height * scaleFactor).toInt()
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()) ?: return
        contentResolver.openOutputStream(imageUri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}