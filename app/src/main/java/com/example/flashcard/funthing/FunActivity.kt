package com.example.flashcard.funthing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.MainActivity
import com.example.flashcard.R
import com.example.flashcard.funthing.FunActivity2

class FunActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun)

        // Thiết lập sự kiện cho các nút
        findViewById<Button>(R.id.btnEasyMode).setOnClickListener {
            navigateToActivity(FunActivity1::class.java)
        }

        findViewById<Button>(R.id.btnHardMode).setOnClickListener {
            navigateToActivity(FunActivity2::class.java)
        }

        findViewById<Button>(R.id.btnScoreList).setOnClickListener {
            navigateToActivity(FunActivityScoreList::class.java)
        }

        findViewById<Button>(R.id.btnAllScoreList).setOnClickListener {
            navigateToActivity(FunActivityALLScoreList::class.java)
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }

    fun goToFunMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
