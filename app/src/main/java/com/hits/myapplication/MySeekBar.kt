package com.hits.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar

class MySeekBar : AppCompatSeekBar {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val thumbPos = (this.progress.toDouble() / this.max * this.width).toInt()
        val paint = Paint()
        val top = this.height.toFloat() - paint.textSize - 120
        paint.color = Color.BLACK
        paint.textSize = 40f
        val textWidth = paint.measureText("" + this.progress)
        c.drawText("" + this.progress, thumbPos - textWidth / 2, top, paint)
    }
}
