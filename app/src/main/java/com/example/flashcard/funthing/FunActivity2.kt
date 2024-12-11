package com.example.flashcard.funthing

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.R
import kotlin.random.Random

class FunActivity2 : AppCompatActivity() {
    private var correctAnswersCount = 0
    private var incorrectAnswersCount = 0
    private var totalScore = 0
    private var answerSelected = false
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun2)

        // Start the first question
        showNewQuestion()

        // Handle "Nộp bài" button click
        findViewById<Button>(R.id.submitButton).setOnClickListener {
            showFinalResult()
        }
    }

    private fun showNewQuestion() {
        answerSelected = false
        resetButtonColors()

        // Generate a random equation
        val question = generateRandomEquation()
        val correctAnswer = evaluateEquation(question)
        val wrongAnswers = generateWrongAnswers(correctAnswer)

        val allAnswers = wrongAnswers.toMutableList()
        allAnswers.add(correctAnswer)
        allAnswers.shuffle()

        // Display question
        findViewById<TextView>(R.id.questionTextView).text = question

        // Display answers
        setupAnswerButtons(allAnswers.take(10), correctAnswer) // Ensure only 10 answers are displayed

        // Start timer
        startTimer()
    }

    private fun resetButtonColors() {
        val buttons = listOf(
            findViewById<Button>(R.id.answerButton1),
            findViewById<Button>(R.id.answerButton2),
            findViewById<Button>(R.id.answerButton3),
            findViewById<Button>(R.id.answerButton4),
            findViewById<Button>(R.id.answerButton5),
            findViewById<Button>(R.id.answerButton6),
            findViewById<Button>(R.id.answerButton7),
            findViewById<Button>(R.id.answerButton8),
            findViewById<Button>(R.id.answerButton9),
            findViewById<Button>(R.id.answerButton10)
        )

        for (button in buttons) {
            button.setBackgroundColor(resources.getColor(R.color.default_button_color, null))
            button.isEnabled = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupAnswerButtons(allAnswers: List<Int>, correctAnswer: Int) {
        val buttons = listOf(
            findViewById<Button>(R.id.answerButton1),
            findViewById<Button>(R.id.answerButton2),
            findViewById<Button>(R.id.answerButton3),
            findViewById<Button>(R.id.answerButton4),
            findViewById<Button>(R.id.answerButton5),
            findViewById<Button>(R.id.answerButton6),
            findViewById<Button>(R.id.answerButton7),
            findViewById<Button>(R.id.answerButton8),
            findViewById<Button>(R.id.answerButton9),
            findViewById<Button>(R.id.answerButton10)
        )

        for (i in buttons.indices) {
            buttons[i].apply {
                text = allAnswers[i].toString()
                setOnClickListener {
                    if (!answerSelected) {
                        if (allAnswers[i] == correctAnswer) {
                            setBackgroundColor(resources.getColor(R.color.correct_answer_color, null))
                            correctAnswersCount++
                            totalScore += 10
                        } else {
                            setBackgroundColor(resources.getColor(R.color.incorrect_answer_color, null))
                            incorrectAnswersCount++
                            totalScore -= 3
                        }
                        // Cập nhật điểm lên TextView
                        findViewById<TextView>(R.id.diem).text = "Điểm: $totalScore"

                        answerSelected = true
                        buttons.forEach { it.isEnabled = false }

                        if (incorrectAnswersCount > 3) {
                            showTooManyMistakesDialog()
                        }
                    }
                }
            }
        }
    }


    private fun startTimer() {
        timer = object : CountDownTimer(5000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timerTextView).text = "Time: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                if (!answerSelected) {
                    incorrectAnswersCount++
                    totalScore -= 3
                    if (incorrectAnswersCount > 3) {
                        showTooManyMistakesDialog()
                    } else {
                        showNewQuestion()
                    }
                } else {
                    showNewQuestion()
                }
            }
        }.start()
    }

    private fun showTooManyMistakesDialog() {
        timer.cancel()
        AlertDialog.Builder(this)
            .setTitle("Quá nhiều lỗi")
            .setMessage("Bạn đã sai quá nhiều câu hỏi!\n\nĐiểm: $totalScore\nCâu đúng: $correctAnswersCount\nCâu sai: $incorrectAnswersCount")
            .setPositiveButton("Kết thúc") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun showFinalResult() {
        timer.cancel()
        AlertDialog.Builder(this)
            .setTitle("Kết quả cuối cùng")
            .setMessage(
                "Điểm: $totalScore\n" +
                        "Câu đúng: $correctAnswersCount\n" +
                        "Câu sai: $incorrectAnswersCount\n" +
                        "Cố gắng hơn nữa nào!"
            )
            .setPositiveButton("OK") { _, _ -> finish() }
            .show()
    }

    private fun generateRandomEquation(): String {
        val operations = listOf("+", "-", "*", "/")
        val numTerms = Random.nextInt(2, 5)
        val terms = mutableListOf<String>()

        for (i in 0 until numTerms) {
            terms.add(Random.nextInt(1, 20).toString())
            if (i < numTerms - 1) {
                terms.add(operations.random())
            }
        }

        return terms.joinToString(" ")
    }

    private fun evaluateEquation(equation: String): Int {
        val tokens = equation.split(" ")
        var result = tokens[0].toInt()

        var i = 1
        while (i < tokens.size) {
            val operator = tokens[i]
            val operand = tokens[i + 1].toInt()
            result = when (operator) {
                "+" -> result + operand
                "-" -> result - operand
                "*" -> result * operand
                "/" -> {
                    if (operand == 0) {
                        // Nếu gặp phép chia cho 0, bỏ qua phép tính này hoặc gán kết quả mặc định
                        return Int.MAX_VALUE // Hoặc giá trị khác tùy mục đích
                    } else {
                        result / operand
                    }
                }
                else -> result
            }
            i += 2
        }

        return result
    }


    private fun generateWrongAnswers(correctAnswer: Int): List<Int> {
        val wrongAnswers = mutableSetOf<Int>()
        while (wrongAnswers.size < 9) {
            val randomAnswer = correctAnswer + Random.nextInt(-20, 20)
            if (randomAnswer != correctAnswer && randomAnswer > 0 && randomAnswer != Int.MAX_VALUE) {
                wrongAnswers.add(randomAnswer)
            }
        }
        return wrongAnswers.toList()
    }

    fun goToFunMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, FunActivity::class.java)
        startActivity(intent)
    }
}
