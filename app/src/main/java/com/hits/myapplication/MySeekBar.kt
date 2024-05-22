package com.hits.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet


class MySeekBar : androidx.appcompat.widget.AppCompatSeekBar {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val thumb = (this.progress.toDouble() / this.max ).toInt()
        //val middle = this.height.toFloat()
        val paint = Paint()
        val top = this.height.toFloat() - paint.textSize -120
        paint.setColor(Color.BLACK)
        paint.textSize = 40f
        val textWidth = paint.measureText("" + this.progress)
        c.drawText("" + this.progress, thumb.toFloat(), top, paint)

    }
}