package com.example.flashcard.addquestion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.data.DatabaseHelper
import com.example.flashcard.MainActivity
import com.example.flashcard.R

class AddQuestionActivity : AppCompatActivity() {

    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText
    private lateinit var addButton: Button
    private lateinit var viewQuestionsButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        questionEditText = findViewById(R.id.questionEditText)
        answerEditText = findViewById(R.id.answerEditText)
        addButton = findViewById(R.id.addButton)
        viewQuestionsButton = findViewById(R.id.viewQuestionsButton)

        databaseHelper = DatabaseHelper(this)

        addButton.setOnClickListener {
            val question = questionEditText.text.toString()
            val answer = answerEditText.text.toString()

            if (question.isNotEmpty() && answer.isNotEmpty()) {
                val rowId = databaseHelper.addQuestion(question, answer)

                if (rowId != -1L) {
                    Toast.makeText(this, "Đã thêm câu hỏi mới vào ngân hàng câu hỏi", Toast.LENGTH_SHORT).show()
                    questionEditText.text.clear()
                    answerEditText.text.clear()
                    Log.d("AddQuestionActivity", "Question added with ID: $rowId")
                } else {
                    Toast.makeText(this, "Đã xảy ra lỗi khi thêm câu hỏi", Toast.LENGTH_SHORT).show()
                    Log.e("AddQuestionActivity", "Failed to add question")
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập câu hỏi và đáp án", Toast.LENGTH_SHORT).show()
            }
        }

        viewQuestionsButton.setOnClickListener {
            val intent = Intent(this, AddQuestionListActivity::class.java)
            startActivity(intent)
        }
    }
    fun goToMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
