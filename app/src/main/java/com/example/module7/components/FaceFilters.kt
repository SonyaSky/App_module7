package com.example.module7.components


import android.graphics.Bitmap
import android.graphics.Color
import org.opencv.core.Rect
import kotlin.math.min

class FaceFilters() {

    private var faces: List<Rect> = emptyList();

    fun inversionFilter(image: Bitmap?): Bitmap? {
        if (image == null) {
            return null
        }
        val filteredImage = image.copy(image.config, true)
        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)
                        val newPixel = Color.rgb(
                            255 - r,
                            255 - g,
                            255 - b
                        )
                        filteredImage.setPixel(x, y, newPixel)
                    }
                }
            }
        }
        return filteredImage
    }

    fun grayscaleFilter(image: Bitmap?): Bitmap? {
        if (image == null) {
            return null
        }
        val filteredImage = image.copy(image.config, true)

        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)
                        val gray = (r + g + b) / 3
                        val newPixel = Color.rgb(gray, gray, gray)
                        filteredImage.setPixel(x, y, newPixel)
                    }
                }
            }
        }
        return filteredImage
    }

    fun blackAndWhiteFilter(image: Bitmap?): Bitmap? {
        if (image == null) {
            return null
        }
        val filteredImage = image.copy(image.config, true)
        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)
                        if ((r+g+b)/3 > 128)
                        {
                            val newPixel = Color.rgb(255,255,255)
                            filteredImage.setPixel(x, y, newPixel)
                        }
                        else
                        {
                            val newPixel = Color.rgb(0,0,0)
                            filteredImage.setPixel(x, y, newPixel)
                        }
                    }
                }
            }
        }
        return filteredImage
    }

    fun sepiaFilter(image: Bitmap?): Bitmap? {
        if (image == null) {
            return null
        }
        val filteredImage = image.copy(image.config, true)
        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)
                        val newPixel = Color.rgb(
                            min(255,(r * 0.393 + g * 0.769 + b * 0.189).toInt()),
                            min(255, (r * 0.349 + g * 0.686 + b * 0.168).toInt()),
                            min(255,(r * 0.272 + g * 0.534 + b * 0.131).toInt())
                        )
                        filteredImage.setPixel(x, y, newPixel)
                    }
                }
            }
        }
        return filteredImage
    }

    fun colorFilter(image: Bitmap?, color:String): Bitmap? {
        if (image == null) {
            return null
        }
        val filteredImage = image.copy(image.config, true)
        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = Color.red(pixel)
                        val g = Color.green(pixel)
                        val b = Color.blue(pixel)
                        val newPixel = when (color) {
                            "blue" -> Color.rgb(0, 0, b)
                            "green" -> Color.rgb(0, g, 0)
                            "red" -> Color.rgb(r, 0, 0)
                            else -> break
                        }
                        filteredImage.setPixel(x, y, newPixel)

                    }
                }
            }
        }
        return filteredImage
    }

    fun tintFilter(image: Bitmap?, color:String): Bitmap? {
        if (image == null) {
            return null
        }
        val tint = when (color) {
            "pink" -> Color.rgb(240, 57, 171)
            "orange" -> Color.rgb(232, 76, 36)
            "yellow" -> Color.rgb(252, 242, 56)
            "purple" -> Color.rgb(176, 38, 255)
            "green" -> Color.rgb(48, 194, 100)
            else -> Color.rgb(0, 0, 0)
        }
        val filteredImage = image.copy(image.config, true)
        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = (Color.red(pixel) + Color.red(tint)) / 2
                        val g = (Color.green(pixel) + Color.green(tint)) / 2
                        val b = (Color.blue(pixel) + Color.blue(tint)) / 2
                        filteredImage.setPixel(x, y, Color.rgb(r.coerceAtMost(255), g.coerceAtMost(255), b.coerceAtMost(255)))

                    }
                }
            }
        }
        return filteredImage
    }

    fun lightFilter(image: Bitmap?, type: String): Bitmap? {
        if (image == null) {
            return null
        }
        val filteredImage = image.copy(image.config, true)
        val factor:Double
        if (type == "light")
            factor = 1.5
        else
            factor = 0.5
        for (face in faces) {
            for (x in face.x until face.x + face.width) {
                for (y in face.y until face.y + face.height) {
                    if (x < image.width && y < image.height) {
                        val pixel = image.getPixel(x, y)
                        val r = (Color.red(pixel) * factor).toInt().coerceAtMost(255)
                        val g = (Color.green(pixel) * factor).toInt().coerceAtMost(255)
                        val b = (Color.blue(pixel) * factor).toInt().coerceAtMost(255)
                        filteredImage.setPixel(x, y, Color.rgb(r, g, b))

                    }
                }
            }
        }
        return filteredImage
    }

    public fun setFaces(faces : List<Rect>) {
        this.faces = faces
    }
}