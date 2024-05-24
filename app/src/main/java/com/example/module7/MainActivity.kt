package com.example.module7

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.module7.components.FaceDetection
import com.example.module7.components.FaceFilters
import com.example.module7.components.ImageFilters
import com.example.module7.components.Masking
import com.example.module7.components.ResizeImage
import com.example.module7.components.RotateImage
import com.example.module7.databinding.ActivityMainBinding
import com.example.module7.fragment.FilterFragment
import com.example.module7.fragment.MaskingFragment
import com.example.module7.fragment.ResizeFragment
import com.example.module7.fragment.RotateFragment
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import java.io.IOException


class MainActivity : AppCompatActivity(), FiltersHandler {
    private lateinit var binding: ActivityMainBinding
    private var originalBitmap: Bitmap? = null
    private val imageFilters = ImageFilters(this)
    private val masking = Masking()
    val rotateImage = RotateImage()
    val resizeImage = ResizeImage()
    var facesDetected = false
    val faceFilters = FaceFilters()
    private val changeImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val imgUri = data?.data
            imgUri?.let { uri ->
                originalBitmap = rotateImage(uri)
                originalBitmap?.let { bitmap ->
                    binding.selectedImage.setImageBitmap(bitmap)
                }
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

        binding.faceBtn.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_cont, FilterFragment())
                commit()
            }
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
            facesDetected = true
            faceFilters.setFaces(faces)
        }

        binding.pickImageBtn.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
            facesDetected = false
        }

        binding.takePhotoBtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            facesDetected = false
        }

        binding.rotateBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_cont, RotateFragment())
                commit()
            }
            facesDetected = false
        }

        binding.scaleBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_cont, ResizeFragment())
                commit()
            }
            facesDetected = false
        }

        /*binding.scaleBtn.setOnClickListener {
            val currentBitmap = (binding.selectedImage.drawable as? BitmapDrawable)?.bitmap
            currentBitmap?.let { bitmap ->
                val scaleFactor = binding.scaleFactorEditText.text.toString().toFloatOrNull()
                scaleFactor?.let {
                    val scaledBitmap = scaleBitmap(bitmap, it)
                    binding.selectedImage.setImageBitmap(scaledBitmap)
                } ?: showToast("Invalid scale factor")
            } ?: showToast("No image selected")
            facesDetected = false
        }*/

        binding.filterBtn.setOnClickListener {
            facesDetected = false
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_cont, FilterFragment())
                commit()
            }
        }

        binding.maskingBtn.setOnClickListener {
            facesDetected = false
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_cont, MaskingFragment())
                commit()
            }
        }

        binding.saveImageBtn.setOnClickListener {
            facesDetected = false
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

    private fun saveBitmap(bitmap: Bitmap) {
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()) ?: return
        contentResolver.openOutputStream(imageUri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun sendToRotateImage(angle: Float) {
        originalBitmap?.let { bitmap ->
            val resultBitmap = rotateImage.rotateBitmap(
                bitmap,
                angle,
                binding.selectedImage.width.toFloat(),
                binding.selectedImage.height.toFloat()
            )
            binding.selectedImage.setImageBitmap(resultBitmap)
        }
    }

    override fun sendToResizeImage(angle: Float) {
        originalBitmap?.let { bitmap ->
            val resultBitmap = resizeImage.resize(
                bitmap,
                angle
            )
            binding.selectedImage.setImageBitmap(resultBitmap)
        }
    }

    override fun sendToInversionFilter() {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.inversionFilter(originalBitmap))
        else binding.selectedImage.setImageBitmap(imageFilters.inversionFilter(originalBitmap))
    }

    override fun sendToGrayscaleFilter() {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.grayscaleFilter(originalBitmap))
        else binding.selectedImage.setImageBitmap(imageFilters.grayscaleFilter(originalBitmap))
    }

    override fun sendToBNWFilter() {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.blackAndWhiteFilter(originalBitmap))
        else binding.selectedImage.setImageBitmap(imageFilters.blackAndWhiteFilter(originalBitmap))
    }

    override fun sendToLightFilter(type: String) {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.lightFilter(originalBitmap, type))
        else binding.selectedImage.setImageBitmap(imageFilters.lightFilter(originalBitmap, type))
    }

    override fun sendToSepiaFilter() {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.sepiaFilter(originalBitmap))
        else binding.selectedImage.setImageBitmap(imageFilters.sepiaFilter(originalBitmap))
    }

    override fun sendToTintFilter(color: String) {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.tintFilter(originalBitmap, color))
        else binding.selectedImage.setImageBitmap(imageFilters.tintFilter(originalBitmap, color))
    }

    override fun sendToColorFilter(color: String) {
        if (facesDetected) binding.selectedImage.setImageBitmap(faceFilters.colorFilter(originalBitmap, color))
        else binding.selectedImage.setImageBitmap(imageFilters.colorFilter(originalBitmap, color))
    }

    override fun sendToMasking(radius: Int, effect: Double, threshold: Int) {
        binding.selectedImage.setImageBitmap(masking.createMask(originalBitmap, radius, effect, threshold))
    }

    override fun takePhotosForButtons(): List<Bitmap?>? {
        val thumbnailWidth = 200
        val thumbnailHeight = 200

        if (originalBitmap != null) {
            val smallImage = createThumbnail(originalBitmap!!, thumbnailWidth, thumbnailHeight)

            return listOf(
                imageFilters.inversionFilter(smallImage),
                imageFilters.grayscaleFilter(smallImage),
                imageFilters.blackAndWhiteFilter(smallImage),
                imageFilters.lightFilter(smallImage, "dark"),
                imageFilters.lightFilter(smallImage, "light"),
                imageFilters.sepiaFilter(smallImage),
                imageFilters.tintFilter(smallImage, "yellow"),
                imageFilters.tintFilter(smallImage, "green"),
                imageFilters.tintFilter(smallImage, "orange"),
                imageFilters.tintFilter(smallImage, "pink"),
                imageFilters.tintFilter(smallImage, "purple"),
                imageFilters.colorFilter(smallImage, "blue"),
                imageFilters.colorFilter(smallImage, "red"),
                imageFilters.colorFilter(smallImage, "green")
            )
        }
        return null
    }

    private fun createThumbnail(image: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}
