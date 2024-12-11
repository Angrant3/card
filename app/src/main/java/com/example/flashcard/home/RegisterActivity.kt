package com.example.flashcard.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Ánh xạ các view
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)

        // Xử lý sự kiện đăng ký
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Kiểm tra xem mật khẩu và xác nhận mật khẩu có trùng khớp không
            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // Lưu thông tin tài khoản vào SharedPreferences
                    sharedPreferences.edit()
                        .putString("username", username)
                        .putString("password", password)
                        .apply()

                    // Thông báo đăng ký thành công
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()

                    // Quay lại màn hình đăng nhập và ngừng back stack
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()  // Close RegisterActivity so the user can't go back
                } else {
                    Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không khớp!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        // Chuyển hướng về màn hình đăng nhập
        tvLogin.setOnClickListener {
            onBackPressed()  // Go back to the login screen
        }
    }
}
