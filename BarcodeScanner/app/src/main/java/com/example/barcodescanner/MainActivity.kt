package com.example.barcodescanner

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.logo).setOnClickListener{
            startActivity(Intent(this, ScannerActivity::class.java))
            finish()
        }
    }
}
