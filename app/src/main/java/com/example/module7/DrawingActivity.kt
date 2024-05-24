package com.example.module7


import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import com.example.module7.components.DrawingView
import com.example.module7.databinding.ActivityDrawingBinding

class DrawingActivity : ComponentActivity() {

    private lateinit var binding: ActivityDrawingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)

        // Default values for brush size and retouch ratio
        val defaultBrushSize = 10f
        val defaultRetouchRatio = 1.0f
        val drawingView = findViewById<DrawingView>(R.id.drawingView)
        val brushSizeSeekBar = findViewById<SeekBar>(R.id.brushSizeSeekBar)
        val brushRatioSeekBar = findViewById<SeekBar>(R.id.brushRatioSeekBar)

        val clearButton = findViewById<Button>(R.id.clearButton)

        binding = ActivityDrawingBinding.inflate(layoutInflater)

        binding.clearButton.setOnClickListener {
            drawingView.clearCanvas()
        }

        binding.colorsBar.black.setOnClickListener {
            drawingView.setBrushColor(Color.BLACK)
        }

        binding.colorsBar.white.setOnClickListener {
            drawingView.setBrushColor(Color.WHITE)
        }

        binding.colorsBar.red.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#F44336"))
        }

        binding.colorsBar.orange.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#FF9800"))
        }

        binding.colorsBar.yellow.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#FFEB3B"))
        }

        binding.colorsBar.green.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("@color/green"))
        }

        binding.colorsBar.lightBlue.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("@color/light_blue"))
        }

        binding.colorsBar.blue.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("@color/blue"))
        }

        binding.colorsBar.purple.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("@color/purple"))
        }

        binding.colorsBar.pink.setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("@color/pink"))
        }

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
