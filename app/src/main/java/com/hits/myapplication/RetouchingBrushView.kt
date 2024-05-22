//package com.hits.myapplication
//
//import android.content.Context
//import android.widget.SeekBar
//import androidx.appcompat.widget.AppCompatImageView
//import com.hits.myapplication.R
//
//class RetouchingBrushView(context: Context) : AppCompatImageView(context){
//    private var brushSize = 10f
//    private var brushRatio = 0.5f
//
//    init {
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
//    }
//
//    val brushSizeSeekBar = findViewById<SeekBar>(R.id.brushSizeSeekBar)
//    val brushRatioSeekBar = findViewById<SeekBar>(R.id.brushRatioSeekBar)
//
//    brushSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            // Update the brush size when the user adjusts the seek bar
//            brushSize = progress.toFloat()
//        }
//
//        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//
//        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//    })
//
//    brushRatioSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            // Update the brush ratio when the user adjusts the seek bar
//            brushRatio = progress.toFloat() / 100f
//        }
//
//        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//
//        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//    })
//}