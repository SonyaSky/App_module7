package com.example.module7

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

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
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var rotatingAngle = 0f
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

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        OpenCVLoader.initDebug()

        binding.faceButton.setOnClickListener {
            val currentBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap

            val mat = Mat()
            Utils.bitmapToMat(currentBitmap, mat)

            val faceDetection = FaceDetection(this)
            val faces: List<Rect> = faceDetection.detectFaces(mat)

            for (rect in faces) {
                Imgproc.rectangle(mat, rect.tl(), rect.br(), Scalar(255.0, 0.0, 0.0), 3)
            }

            val resultBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(mat, resultBitmap)
            currentBitmap?.let { currentBitmap ->

                binding.selectedImage.setImageBitmap(resultBitmap)
            }

        }

        binding.pickImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        binding.takePhotoButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

        }

        binding.rotateImage.setOnClickListener { rangeSlider ->
            val currentBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            currentBitmap?.let { bitmap ->
                val rotatedBitmap = rotateBitmap(bitmap, rotatingAngle)
                binding.selectedImage.setImageBitmap(rotatedBitmap)
            }
        }



        binding.rangeSlider.addOnChangeListener { rangeSlider, value, fromUser ->
            rotatingAngle = rangeSlider.values[0]
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

        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val scaleFactor = minOf(
            binding.selectedImage.width.toFloat() / originalWidth,
            binding.selectedImage.height.toFloat() / originalHeight
        )
        matrix.postScale(scaleFactor, scaleFactor)

        val rotatedBitmap = Bitmap.createBitmap(
            bitmap.width, bitmap.height, bitmap.config
        )

        val canvas = Canvas(rotatedBitmap)
        val translateX = (rotatedBitmap.width / 2).toFloat()
        val translateY = (rotatedBitmap.height / 2).toFloat()

        matrix.postTranslate(translateX, translateY)
        canvas.setMatrix(matrix)
        canvas.clipRect(0f, 0f, originalWidth.toFloat(), originalHeight.toFloat())
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        return rotatedBitmap
    }


    private fun scaleBitmap(bitmap: Bitmap, scaleFactor: Float): Bitmap {
        val width = (bitmap.width * scaleFactor).toInt()
        val height = (bitmap.height * scaleFactor).toInt()
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
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

class FaceDetection(context: Context) {

    private var cascadeClassifier: CascadeClassifier? = null

    init {
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.haarcascade_frontalface_alt2)
            val cascadeDir: File = context.getDir("cascade", Context.MODE_PRIVATE)
            val mCascadeFile = File(cascadeDir, "haarcascade_frontalface_alt2.xml")
            val outputStream = FileOutputStream(mCascadeFile)

            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            inputStream.close()
            outputStream.close()

            cascadeClassifier = CascadeClassifier(mCascadeFile.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun detectFaces(inputImage: Mat): List<Rect> {
        val faces = MatOfRect()
        cascadeClassifier?.detectMultiScale(inputImage, faces, 1.1, 2, 2, Size(30.0, 30.0), Size())
        return faces.toList()
    }
}