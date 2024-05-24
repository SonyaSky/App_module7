package com.example.module7.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var brushSize = 10f
    private var retouchRatio = 1.0f
    private var paint = Paint()
    private var path = Path()
    private var canvasBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    init {
        paint.color = 0xFF000000.toInt()
        paint.isAntiAlias = true
        paint.strokeWidth = brushSize
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, null)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(touchX, touchY)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
                drawCanvas?.drawPath(path, paint)
                path.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun setBrushSize(size: Float) {
        brushSize = size
        paint.strokeWidth = brushSize
    }

    fun setBrushColor(color: Int) {
        paint.color = color
    }

    fun setRetouchRatio(ratio: Float) {
        retouchRatio = ratio
        // Modify paint or brush effect based on retouch ratio if needed
        // For now, this just sets a new color based on ratio for demonstration
        val alpha = (255 * ratio).toInt()
        paint.color = (alpha shl 24) or (paint.color and 0x00FFFFFF)
    }

    fun clearCanvas() {
        drawCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        invalidate()
    }
}