package com.example.flashcard.tracnghiem

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.R
import kotlin.random.Random

class QuizNormalActivity : AppCompatActivity() {
    private var questionList: List<Pair<Int, Int>> = listOf()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var totalAnswersCount = 0
    private var answerSelected = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_normal)

        // Tạo danh sách câu hỏi từ bảng cửu chương
        questionList = generateMultiplicationQuestions()
        showQuestion()

        // Thiết lập sự kiện cho nút Next
        findViewById<Button>(R.id.nextButton).setOnClickListener {
            if (answerSelected) {
                currentQuestionIndex++
                if (currentQuestionIndex < questionList.size) {
                    showQuestion()
                } else {
                    // Hiển thị AlertDialog sau khi hoàn thành
                    showResultDialog()
                }
            }
        }
    }

    private fun showQuestion() {
        answerSelected = false // Đặt lại trạng thái trả lời
        resetButtonColors() // Đặt lại màu sắc của các nút trả lời

        if (currentQuestionIndex >= questionList.size) return

        val (multiplier, multiplicand) = questionList[currentQuestionIndex]
        val correctAnswer = multiplier * multiplicand
        val wrongAnswers = generateWrongAnswers(correctAnswer)

        val allAnswers = wrongAnswers.toMutableList()
        allAnswers.add(correctAnswer)
        allAnswers.shuffle()

        // Hiển thị câu hỏi
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        questionTextView.text = "$multiplier x $multiplicand = ?"

        // Hiển thị số thứ tự câu hỏi
        val questionCountTextView = findViewById<TextView>(R.id.questionCountTextView)
        questionCountTextView.text = "Câu hỏi số : ${currentQuestionIndex + 1}"

        // Đặt văn bản và xử lý sự kiện cho các nút trả lời
        setupAnswerButtons(allAnswers, correctAnswer)
    }

    private fun resetButtonColors() {
        val buttons = listOf(
            findViewById<Button>(R.id.answerButton1),
            findViewById<Button>(R.id.answerButton2),
            findViewById<Button>(R.id.answerButton3),
            findViewById<Button>(R.id.answerButton4)
        )

        for (button in buttons) {
            button.setBackgroundColor(Color.parseColor("#FFBB86FC")) // Màu mặc định
            button.isEnabled = true // Bật lại nút
        }
    }

    private fun setupAnswerButtons(allAnswers: List<Int>, correctAnswer: Int) {
        val buttons = listOf(
            findViewById<Button>(R.id.answerButton1),
            findViewById<Button>(R.id.answerButton2),
            findViewById<Button>(R.id.answerButton3),
            findViewById<Button>(R.id.answerButton4)
        )

        for (i in buttons.indices) {
            buttons[i].apply {
                text = allAnswers[i].toString()
                setOnClickListener {
                    if (allAnswers[i] == correctAnswer) {
                        setBackgroundColor(Color.GREEN) // Đúng
                        correctAnswersCount++ // Ghi nhận đáp án đúng
                    } else {
                        setBackgroundColor(Color.RED) // Sai
                    }
                    totalAnswersCount++ // Tăng tổng số câu trả lời
                    answerSelected = true
                    // Khóa các nút sau khi chọn
                    buttons.forEach { it.isEnabled = false }
                }
            }
        }
    }

    private fun generateWrongAnswers(correctAnswer: Int): List<Int> {
        val wrongAnswers = mutableSetOf<Int>()
        while (wrongAnswers.size < 3) {
            val randomOperation = (1..4).random()
            val wrongAnswer = when (randomOperation) {
                1 -> correctAnswer + Random.nextInt(1, 11)  // Cộng
                2 -> correctAnswer - Random.nextInt(1, 11)  // Trừ
                3 -> correctAnswer * Random.nextInt(1, 4)   // Nhân
                4 -> if (correctAnswer > 1) correctAnswer / Random.nextInt(2, 4) else correctAnswer + 1  // Chia
                else -> correctAnswer + 1
            }
            if (wrongAnswer != correctAnswer && wrongAnswer > 0) {
                wrongAnswers.add(wrongAnswer)
            }
        }
        return wrongAnswers.toList()
    }

    private fun generateMultiplicationQuestions(): List<Pair<Int, Int>> {
        val questions = mutableListOf<Pair<Int, Int>>()
        for (i in 1..10) {
            val multiplier = Random.nextInt(1, 10) // Bảng cửu chương từ 1 đến 9
            val multiplicand = Random.nextInt(1, 10) // Phép nhân với số ngẫu nhiên
            questions.add(Pair(multiplier, multiplicand))
        }
        return questions
    }

    private fun showResultDialog() {
        val message = if (correctAnswersCount == totalAnswersCount) {
            "Chúc mừng bạn đã hoàn thành xuất sắc!"
        } else {
            "Bạn đã làm rất tốt!"
        }

        val dialogMessage = """
            Bạn trả lời đúng $correctAnswersCount/$totalAnswersCount câu!
            
            $message
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Kết quả")
            .setMessage(dialogMessage)
            .setPositiveButton("Quay lại") { _, _ ->
                finish() // Kết thúc Activity để quay lại màn hình trước
            }
            .setCancelable(false) // Không cho phép thoát bằng cách nhấn ra ngoài
            .show()
    }

    fun goToMainquestion(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, QuizMainActivity::class.java)
        startActivity(intent)
    }
}
