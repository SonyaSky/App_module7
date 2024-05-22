package com.hits.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

//class BrushView(context: Context?) : View(context) {
//
//    private val paint: Paint = Paint()
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        paint.apply{
//            isAntiAlias = true
//            color = Color.RED
//        }
//    }
//
//}


class BrushView(context: Context, var brushSize: Int) : View(context) {

    private var brushPaint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(x, y, brushSize, canvas)
    }

    fun drawCircle(x: Float, y: Float, fl: Int, canvas:Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.strokeWidth = fl.toFloat()
        canvas.drawCircle(x, y, fl / 2f, paint)
    }


}