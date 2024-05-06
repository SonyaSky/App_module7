package com.hits.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.content.Intent
//import android.graphics.Color
//import android.graphics.Bitmap
//import android.graphics.drawable.BitmapDrawable
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.ui.Modifier
//import android.widget.Button
//import android.widget.ImageView
//import com.hits.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {

    private lateinit var imageFilter: ImageFilters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        imageFilter = ImageFilters(this)
        imageFilter.setOnClickListeners()

        val intent = Intent(this, RetouchingActivity::class.java)
        startActivity(intent)
        val retouchButton = findViewById<Button>(R.id.retouchButton)

        retouchButton.setOnClickListener {
            val intent = Intent(this,RetouchingActivity::class.java)
            startActivity(intent)
        }

    }

}

