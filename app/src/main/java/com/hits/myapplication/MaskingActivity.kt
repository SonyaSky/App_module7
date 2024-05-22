package com.hits.myapplication

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min


class MaskingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masking)

        val imageView = findViewById<AppCompatImageView>(R.id.imageView)
        val originalImage = (imageView.drawable as BitmapDrawable).bitmap
        val unsharpMask = UnsharpMask(this, originalImage)
        unsharpMask.setOnClickListeners(imageView)



    }
}
