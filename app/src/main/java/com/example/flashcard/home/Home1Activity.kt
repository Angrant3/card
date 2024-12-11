package com.example.flashcard.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.MainActivity
import com.example.flashcard.R

class Home1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home1) // Liên kết với home1.xml
    }

    // Hàm này sẽ được gọi khi người dùng nhấn nút picnexthome
    fun goToHome2(view: View) {
        // Sử dụng Intent để chuyển sang Home2Activity
        val intent = Intent(this, Home2Activity::class.java)
        startActivity(intent)
    }
    fun goToMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
