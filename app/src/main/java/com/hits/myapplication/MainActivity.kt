package com.hits.myapplication


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.AppCompatImageView
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
//import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ComponentActivity() {

    private lateinit var imageFilters: ImageFilters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val imageView = findViewById<AppCompatImageView>(R.id.imageView)
        val originalImage = (imageView.drawable as BitmapDrawable).bitmap
        val imageFilters = ImageFilters(this, originalImage)
        imageFilters.setOnClickListeners(imageView)

        val nextPage = findViewById<Button>(R.id.retouchButton)
        val nextPage1 = findViewById<Button>(R.id.maskButton)
        val nextPage2 = findViewById<Button>(R.id.drawingButton)

        nextPage2.setOnClickListener {
            val intent = Intent(this, DrawingActivity::class.java)
            startActivity(intent)
        }

        nextPage1.setOnClickListener {
            val intent1 = Intent(this, MaskingActivity::class.java)
            startActivity(intent1)
        }

        nextPage.setOnClickListener {
            val intent1 = Intent(this, RetouchingActivity::class.java)
            startActivity(intent1)
        }

//        val brushSizeSeekBar = findViewById<CustomSeekBar>(R.id.brushSizeSeekBar)
//        val brushSizeTextView = findViewById<TextView>(R.id.brushSizeTextView)
//
//        brushSizeSeekBar.setProgressTextView(brushSizeTextView)
//
//        val brushRatioSeekBar = findViewById<CustomSeekBar>(R.id.brushRatioSeekBar)
//        val brushRatioTextView = findViewById<TextView>(R.id.brushRatioTextView)
//
//        brushRatioSeekBar.setProgressTextView(brushRatioTextView)

    }
}