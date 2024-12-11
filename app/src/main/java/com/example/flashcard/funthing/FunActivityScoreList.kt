package com.example.flashcard.funthing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.R
import com.example.flashcard.data.ScoreRecord

class FunActivityScoreList : AppCompatActivity() {
    private val scoreHistory = mutableListOf<ScoreRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun_score_list)

        // Load user score history from database or local storage
        loadScoreHistory()

        // Display score data
        displayScoreData()
    }

    private fun loadScoreHistory() {
        // Ví dụ: Dữ liệu này có thể lấy từ cơ sở dữ liệu Firebase hoặc SharedPreferences
        scoreHistory.clear()

        // Thêm dữ liệu mẫu (thay bằng dữ liệu thật từ tài khoản)
        scoreHistory.add(ScoreRecord(1, 80, 1670100000000))
        scoreHistory.add(ScoreRecord(2, 95, 1670200000000))
        scoreHistory.add(ScoreRecord(3, 75, 1670300000000))
        scoreHistory.add(ScoreRecord(4, 100, 1670400000000))

        // Sắp xếp danh sách theo thứ tự giảm dần của attemptNumber
        scoreHistory.sortByDescending { it.attemptNumber }
    }

    private fun displayScoreData() {
        val highestScore = scoreHistory.maxByOrNull { it.score }
        val latestScore = scoreHistory.firstOrNull()

        val scoreListTextView = findViewById<TextView>(R.id.scoreListTextView)
        val stringBuilder = StringBuilder()

        // Hiển thị điểm cao nhất và lần chơi tương ứng
        highestScore?.let {
            stringBuilder.append("Điểm cao nhất: ${it.score} (Lần: ${it.attemptNumber})\n\n")
        }

        // Hiển thị điểm của lần chơi mới nhất
        latestScore?.let {
            stringBuilder.append("Điểm mới nhất: ${it.score} (Lần: ${it.attemptNumber})\n\n")
        }

        // Hiển thị danh sách tất cả điểm
        stringBuilder.append("Lịch sử điểm số:\n")
        for (record in scoreHistory) {
            stringBuilder.append("Lần: ${record.attemptNumber} - Điểm: ${record.score}\n")
        }

        // Cập nhật TextView
        scoreListTextView.text = stringBuilder.toString()
    }

    fun goToFunMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, FunActivity::class.java)
        startActivity(intent)
    }
}
