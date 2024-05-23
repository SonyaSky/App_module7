package com.hits.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class RetouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var brushSize = 10f
    private var retouchRatio = 1.0f
    private var paint = Paint()
    private var path = Path()
    private var canvasBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    init {
        paint.color = 0xFFFF0000.toInt()
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
                applyMeanFilter(touchX.toInt(), touchY.toInt())
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(touchX, touchY)
                applyMeanFilter(touchX.toInt(), touchY.toInt())
            }
            MotionEvent.ACTION_UP -> {
                path.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun applyMeanFilter(x: Int, y: Int) {
        val radius = brushSize.toInt()
        val pixelValues = mutableListOf<Int>()

        for (dx in -radius..radius) {
            for (dy in -radius..radius) {
                val pixelX = (x + dx).coerceIn(0, canvasBitmap!!.width - 1)
                val pixelY = (y + dy).coerceIn(0, canvasBitmap!!.height - 1)
                pixelValues.add(canvasBitmap!!.getPixel(pixelX, pixelY))
            }
        }

        val meanPixel = computeMeanPixel(pixelValues)
        for (dx in -radius..radius) {
            for (dy in -radius..radius) {
                val pixelX = (x + dx).coerceIn(0, canvasBitmap!!.width - 1)
                val pixelY = (y + dy).coerceIn(0, canvasBitmap!!.height - 1)
                canvasBitmap!!.setPixel(pixelX, pixelY, meanPixel)
            }
        }
    }

    private fun computeMeanPixel(pixels: List<Int>): Int {
        var redSum = 0
        var greenSum = 0
        var blueSum = 0
        var alphaSum = 0

        for (pixel in pixels) {
            alphaSum += Color.alpha(pixel)
            redSum += Color.red(pixel)
            greenSum += Color.green(pixel)
            blueSum += Color.blue(pixel)
        }

        val count = pixels.size
        val meanAlpha = (alphaSum / count).coerceIn(0, 255)
        val meanRed = (redSum / count).coerceIn(0, 255)
        val meanGreen = (greenSum / count).coerceIn(0, 255)
        val meanBlue = (blueSum / count).coerceIn(0, 255)

        return Color.argb(meanAlpha, meanRed, meanGreen, meanBlue)
    }

    fun setBrushSize(size: Float) {
        brushSize = size
        paint.strokeWidth = brushSize
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