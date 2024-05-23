package com.hits.myapplication


import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.activity.ComponentActivity

class DrawingActivity : ComponentActivity() {

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

        clearButton.setOnClickListener {
            drawingView.clearCanvas()
        }

        findViewById<Button>(R.id.black).setOnClickListener {
            drawingView.setBrushColor(Color.BLACK)
        }
        findViewById<Button>(R.id.white).setOnClickListener {
            drawingView.setBrushColor(Color.WHITE)
        }
        findViewById<Button>(R.id.red).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#F44336"))
        }
        findViewById<Button>(R.id.blue).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#0000FF"))
        }
        findViewById<Button>(R.id.green).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#4CAF50"))
        }
        findViewById<Button>(R.id.yellow).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#FFEB3B"))
        }
        findViewById<Button>(R.id.orange).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#FF9800"))
        }
        findViewById<Button>(R.id.pink).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#E91E63"))
        }
        findViewById<Button>(R.id.purple).setOnClickListener {
            drawingView.setBrushColor(Color.parseColor("#9C27B0"))
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
