package com.example.flashcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.flashcard.addquestion.AddQuestionActivity
import com.example.flashcard.data.Flashcard
import com.example.flashcard.funthing.FunActivity
import com.example.flashcard.funthing.FunActivity1
import com.example.flashcard.home.HomeActivity
import com.example.flashcard.ontap.ReviewActivity
import com.example.flashcard.tracnghiem.QuizMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var Flashcards: List<Flashcard>
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Thiết lập Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        Flashcards = createFlashcards() // Khởi tạo flashcards

        // Thiết lập click listener cho các nút
        setUpButtonClickListeners()


        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Kiểm tra trạng thái đăng nhập khi mở lại ứng dụng
        if (!sharedPreferences.getBoolean("is_logged_in", false)) {
            // Nếu chưa đăng nhập, quay lại màn hình HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Ánh xạ nút đăng xuất
        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logout() // Đăng xuất khi nhấn nút
        }
    }

    private fun setUpButtonClickListeners() {
        // Danh sách các nút và hoạt động tương ứng
        val buttonActivities = mapOf(
            R.id.buttonReview to ReviewActivity::class.java,
            R.id.buttonAddQuestion to AddQuestionActivity::class.java,
            R.id.buttonQuiz to QuizMainActivity::class.java,
            R.id.buttonFun to FunActivity::class.java

        )

        // Gán click listener cho mỗi nút
        buttonActivities.forEach { (buttonId, activityClass) ->
            findViewById<Button>(buttonId).setOnClickListener {
                navigateToActivity(activityClass)
            }
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass)) // Điều hướng đến hoạt động
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Tạo menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_review -> {
                // Điều hướng đến ReviewActivity khi nhấn vào Ôn tập
                navigateToActivity(ReviewActivity::class.java)
                true
            }
            R.id.action_quiz -> {
                navigateToActivity(QuizMainActivity::class.java) // Điều hướng đến QuizActivity
                true
            }
            R.id.action_fun -> {
                navigateToActivity(FunActivity::class.java) // Điều hướng đến FunActivity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createFlashcards(): List<Flashcard> {
        // Tạo danh sách flashcards
        return (1..9).flatMap { i ->
            (1..9).map { j ->
                Flashcard("$i x $j", "${i * j}")
            }
        }
    }


    private fun logout() {
        // Xóa trạng thái đăng nhập trong SharedPreferences
        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_logged_in", false).apply()
        // Hiển thị thông báo đăng xuất thành công
        Toast.makeText(this, "Bạn đã đăng xuất thành công!", Toast.LENGTH_SHORT).show()

        // Chuyển đến màn hình đăng nhập
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
