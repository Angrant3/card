package com.example.flashcard.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.MainActivity
import com.example.flashcard.R

class Home4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home4) // Liên kết với home1.xml
    }

    fun goToMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
