package com.hits.myapplication


import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatImageView

class RetouchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_retouch)

//        val drawing = BrushView(this)
//        setContentView(drawing)
//
//
//        var brushView = findViewById<BrushView>(R.id.brushView)
//        val brushSizeSeekBar = findViewById<MySeekBar>(R.id.brushSizeSeekBar)
//
//
//
//        brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                brushView.brushSize = progress
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//        })
//
//        val imageView = findViewById<AppCompatImageView>(R.id.imageView)
//        imageView.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                brushView.visibility = View.VISIBLE
//                brushView.drawCircle(event.x, event.y, brushView.brushSize / 2f)
//            } else if (event.action == MotionEvent.ACTION_UP) {
//                brushView.visibility = View.INVISIBLE
//            }
//            true
//        }
    }
}
