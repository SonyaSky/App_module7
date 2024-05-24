package com.example.module7.components

import android.content.Context
import com.example.module7.R
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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