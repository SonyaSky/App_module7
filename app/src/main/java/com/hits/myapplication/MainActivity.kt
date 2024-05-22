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

//        val gifImageView = findViewById<ImageView>(R.id.gifImageView)
//
//        Glide.with(this)
//            .asGif()
//            .load(R.drawable.gem_gif) // Replace with your GIF resource
//            .into(gifImageView)

        val imageView = findViewById<AppCompatImageView>(R.id.imageView)
        val originalImage = (imageView.drawable as BitmapDrawable).bitmap
        val imageFilters = ImageFilters(this, originalImage)
        imageFilters.setOnClickListeners(imageView)

        val nextPage = findViewById<Button>(R.id.retouchButton)
        val nextPage1 = findViewById<Button>(R.id.maskButton)

        nextPage.setOnClickListener {
            val intent = Intent(this, RetouchActivity::class.java)
            startActivity(intent)
        }

        nextPage1.setOnClickListener {
            val intent1 = Intent(this, MaskingActivity::class.java)
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