package com.example.flashcard.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import com.example.flashcard.MainActivity
import com.example.flashcard.R


class Home2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home2) // Liên kết với home2.xml
    }

    // Hàm này sẽ được gọi khi người dùng nhấn nút picnexthome
    fun goToHome3(view: View) {
        // Sử dụng Intent để chuyển sang Home3Activity
        val intent = Intent(this, Home3Activity::class.java)
        startActivity(intent)
    }
    fun goToMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
