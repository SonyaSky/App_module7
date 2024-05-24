package com.hits.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class RetouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var brushSize = 10f
    private var retouchRatio = 1.0f
    private var paint = Paint()
    private var path = Path()
    private var canvasBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null
    private var originalBitmap: Bitmap? = null

    init {
        paint.color = 0x00000000.toInt()
        paint.isAntiAlias = true
        paint.strokeWidth = brushSize
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.alpha = 0
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvasBitmap?.let {
            val srcRect = Rect(0, 0, it.width, it.height)
            val dstRect = Rect(0, 0, width, height)
            canvas.drawBitmap(it, srcRect, dstRect, null)
            canvas.drawPath(path, paint)
        }

//        originalBitmap?.let {
//            val srcRect = Rect(0, 0, it.width, it.height)
//            val dstRect = Rect(width - it.width, height - it.height, width, height)
//            canvas.drawBitmap(it, srcRect, dstRect, null)
//        }
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

    fun setImageBitmap(bitmap: Bitmap) {
        canvasBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        originalBitmap = bitmap
        invalidate()
    }


    private fun applyMeanFilter(x: Int, y: Int) {
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
                val originalPixel = canvasBitmap!!.getPixel(pixelX, pixelY)
                val blendedPixel = blendPixels(originalPixel, meanPixel, retouchRatio)
                canvasBitmap!!.setPixel(pixelX, pixelY, blendedPixel)
            }
        }
        invalidate()
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

    private fun blendPixels(originalPixel: Int, meanPixel: Int, ratio: Float): Int {
        val srcA = Color.alpha(meanPixel) * ratio
        val srcR = Color.red(meanPixel) * ratio
        val srcG = Color.green(meanPixel) * ratio
        val srcB = Color.blue(meanPixel) * ratio

        val destA = Color.alpha(originalPixel) * (1 - ratio)
        val destR = Color.red(originalPixel) * (1 - ratio)
        val destG = Color.green(originalPixel) * (1 - ratio)
        val destB = Color.blue(originalPixel) * (1 - ratio)

        val alpha = (srcA + destA).toInt()
        val red = ((srcR + destR) / alpha * srcA).toInt()
        val green = ((srcG + destG) / alpha * srcA).toInt()
        val blue = ((srcB + destB) / alpha * srcA).toInt()

        return Color.argb(alpha, red, green, blue)
    }


    fun setBrushSize(size: Float) {
        brushSize = size
        paint.strokeWidth = brushSize
    }

    fun setRetouchRatio(ratio: Int) {
        retouchRatio = ratio /100f
    }

    fun clearCanvas() {
        drawCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        invalidate()
    }

}
