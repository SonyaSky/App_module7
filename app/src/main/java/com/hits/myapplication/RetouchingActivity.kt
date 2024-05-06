package com.hits.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RetouchingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retouching)

        // Do something here
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}