package com.example.flashcard.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.MainActivity
import com.example.flashcard.R

class HomeActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Kiểm tra trạng thái đăng nhập
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            navigateToMainActivity() // Chuyển đến MainActivity
            return // Dừng lại không cần xử lý đăng nhập nữa
        }

        // Ánh xạ các view
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (username == savedUsername && password == savedPassword) {
                // Lưu trạng thái đăng nhập
                sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
                navigateToHome1Activity() // Chuyển đến Home1Activity sau khi đăng nhập thành công
            } else {
                Toast.makeText(this, "Sai tên người dùng hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome1Activity() {
        val intent = Intent(this, Home1Activity::class.java) // Điều hướng tới Home1Activity
        startActivity(intent)
        finish()
    }
}
