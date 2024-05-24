package com.hits.myapplication


import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.ComponentActivity

class RetouchingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retouching)

        // Default values for brush size and retouch ratio
        val defaultBrushSize = 10f
        val defaultRetouchRatio = 1.0f
        val retouchView = findViewById<RetouchView>(R.id.drawingView)
        val brushSizeSeekBar = findViewById<SeekBar>(R.id.brushSizeSeekBar)
        val brushRatioSeekBar = findViewById<SeekBar>(R.id.brushRatioSeekBar)

        retouchView.setBrushSize(defaultBrushSize)
        retouchView.setRetouchRatio(defaultRetouchRatio)

        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            retouchView.clearCanvas()
        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.post {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            retouchView.setImageBitmap(bitmap)
        }

        // Set up brush size seek bar
        brushSizeSeekBar.max = 100
        brushSizeSeekBar.progress = defaultBrushSize.toInt()
        brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newSize = progress.toFloat()
                retouchView.setBrushSize(newSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up retouch ratio seek bar
        brushRatioSeekBar.max = 100
        brushRatioSeekBar.progress = (defaultRetouchRatio * 100).toInt()
        brushRatioSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newRatio = progress / 100f
                retouchView.setRetouchRatio(newRatio)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
