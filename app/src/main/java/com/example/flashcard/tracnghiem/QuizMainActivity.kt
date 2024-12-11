package com.example.flashcard.tracnghiem

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.MainActivity
import com.example.flashcard.R

class QuizMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
    }

    fun goToezmode(view: View) {
        val intent = Intent(this, QuizNormalActivity::class.java)
        startActivity(intent)
    }

    fun goTohardmode(view: View) {
        val intent = Intent(this, QuizHardActivity::class.java)
        startActivity(intent)
    }
    fun goToMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
