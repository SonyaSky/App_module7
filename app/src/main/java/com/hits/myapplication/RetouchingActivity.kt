package com.hits.myapplication


import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.ComponentActivity

class RetouchingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retouching)

        // Default values for brush size and retouch ratio
        val defaultBrushSize = 10f
        val defaultRetouchRatio = 1.0f
        val drawingView = findViewById<DrawingView>(R.id.drawingView)
        val brushSizeSeekBar = findViewById<SeekBar>(R.id.brushSizeSeekBar)
        val brushRatioSeekBar = findViewById<SeekBar>(R.id.brushRatioSeekBar)


        drawingView.setBrushSize(defaultBrushSize)
        drawingView.setRetouchRatio(defaultRetouchRatio)

        // Set up brush size seek bar
        brushSizeSeekBar.max = 100
        brushSizeSeekBar.progress = defaultBrushSize.toInt()
        brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newSize = progress.toFloat()
                drawingView.setBrushSize(newSize)
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
                drawingView.setRetouchRatio(newRatio)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
