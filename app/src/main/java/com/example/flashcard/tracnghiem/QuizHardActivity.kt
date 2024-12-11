package com.example.flashcard.tracnghiem

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.data.DatabaseHelper
import com.example.flashcard.data.Question
import com.example.flashcard.R
import kotlin.random.Random

class QuizHardActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private var questionList: List<Question> = listOf()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var totalAnswersCount = 0
    private var answerSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_hard)

        // Use task.db for this activity (questions table)
        databaseHelper = DatabaseHelper(this)
        questionList = databaseHelper.getRandomQuestions(10) // Lấy 10 câu hỏi ngẫu nhiên

        showQuestion()
    }

    private fun showQuestion() {
        answerSelected = false // Đặt lại trạng thái trả lời
        resetButtonColors() // Đặt lại màu sắc của các nút trả lời

        if (currentQuestionIndex >= questionList.size) {
            // Khi hoàn tất quiz, hiển thị kết quả
            showResultDialog()
            return
        }

        val currentQuestion = questionList[currentQuestionIndex]
        val questionText = currentQuestion.question
        val correctAnswer = currentQuestion.answer
        val wrongAnswers = generateWrongAnswers(correctAnswer)

        val allAnswers = wrongAnswers.toMutableList()
        allAnswers.add(correctAnswer)
        allAnswers.shuffle()

        // Hiển thị câu hỏi
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        questionTextView.text = questionText

        // Hiển thị số thứ tự câu hỏi
        val questionCountTextView = findViewById<TextView>(R.id.questionCountTextView)
        questionCountTextView.text = "Câu hỏi số: ${currentQuestionIndex + 1}"

        // Đặt văn bản và xử lý sự kiện cho các nút trả lời
        setupAnswerButtons(allAnswers, correctAnswer)
    }

    private fun setupAnswerButtons(allAnswers: List<String>, correctAnswer: String) {
        val buttons = listOf(
            findViewById<Button>(R.id.answerButton1),
            findViewById<Button>(R.id.answerButton2),
            findViewById<Button>(R.id.answerButton3),
            findViewById<Button>(R.id.answerButton4)
        )

        for (i in buttons.indices) {
            buttons[i].apply {
                text = allAnswers[i]
                setBackgroundColor(Color.parseColor("#FFBB86FC")) // Màu mặc định
                isEnabled = true // Bật nút
                setOnClickListener {
                    if (!answerSelected) { // Đảm bảo chỉ chọn 1 câu trả lời
                        if (allAnswers[i] == correctAnswer) {
                            setBackgroundColor(Color.GREEN) // Đúng
                            correctAnswersCount++ // Cập nhật số câu đúng
                        } else {
                            setBackgroundColor(Color.RED) // Sai
                        }
                        totalAnswersCount++ // Cập nhật tổng số câu trả lời
                        answerSelected = true

                        // Vô hiệu hóa các nút còn lại
                        buttons.forEach { it.isEnabled = false }

                        // Chuyển sang câu hỏi tiếp theo sau 1 giây
                        Handler(Looper.getMainLooper()).postDelayed({
                            currentQuestionIndex++
                            showQuestion()
                        }, 1000)
                    }
                }
            }
        }
    }

    private fun generateWrongAnswers(correctAnswer: String): List<String> {
        val wrongAnswers = mutableSetOf<String>()
        while (wrongAnswers.size < 3) {
            val randomOperation = (1..4).random()
            val wrongAnswer = try {
                when (randomOperation) {
                    1 -> (correctAnswer.toInt() + Random.nextInt(1, 11)).toString() // Cộng
                    2 -> (correctAnswer.toInt() - Random.nextInt(1, 11)).toString() // Trừ
                    3 -> (correctAnswer.toInt() * Random.nextInt(1, 4)).toString() // Nhân
                    4 -> {
                        // Kiểm tra trước khi chia để tránh chia cho 0 hoặc chia không hợp lý
                        val divisor = Random.nextInt(2, 4)
                        val result = if (correctAnswer.toInt() > 1) {
                            correctAnswer.toInt() / divisor
                        } else {
                            correctAnswer.toInt() + 1
                        }
                        result.toString() // Chia an toàn
                    }
                    else -> (correctAnswer.toInt() + 1).toString() // Cộng
                }
            } catch (e: ArithmeticException) {
                // Nếu có lỗi trong phép chia (chia cho 0), tiếp tục với một phép toán khác
                continue
            }

            // Kiểm tra nếu kết quả sai không trùng với đáp án đúng
            if (wrongAnswer != correctAnswer) {
                wrongAnswers.add(wrongAnswer)
            }
        }
        return wrongAnswers.toList()
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
}
