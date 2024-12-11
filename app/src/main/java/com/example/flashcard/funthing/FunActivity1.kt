package com.example.flashcard.funthing

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.flashcard.MainActivity
import com.example.flashcard.R
import com.example.flashcard.data.Flashcard

class FunActivity1 : AppCompatActivity() {
    private final var clickCount:Int = 0
    private var isImageP1 = true // Biến theo dõi hình ảnh hiện tại
    private lateinit var flashcards: List<Flashcard> // Danh sách flashcards
    private var currentIndex: Int = 0 // Chỉ số hiện tại
    private var multiplicationIndex: Int = 1 // Số nhân hiện tại

    private val handler = Handler(Looper.getMainLooper()) // Khởi tạo Handler với Looper chính
    private var isLongPressing = false // Biến theo dõi trạng thái nhấn lâu


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun1)

        // Lấy layout chính để thiết lập sự kiện click
        val mainLayout = findViewById<ConstraintLayout>(R.id.main_layout)
        val imageView = findViewById<ImageView>(R.id.background_image)
        val clickCounterText = findViewById<TextView>(R.id.click_counter_text)
        val multiplicationTableText = findViewById<TextView>(R.id.multiplication_table_text) // TextView để hiển thị bảng nhân

        // Thiết lập hình ảnh nền ban đầu
        imageView.setImageResource(R.drawable.p1)

        // Sự kiện khi người dùng nhấn vào màn hình
        mainLayout.setOnClickListener {
            clickCount++
            // Cập nhật số lần nhấn trong TextView
            clickCounterText.text = "Số lần nhấn: $clickCount"

            // Chuyển đổi giữa hình ảnh p1.jpg và p2.jpg
            if (isImageP1) {
                imageView.setImageResource(R.drawable.p2) // Chuyển sang p2.jpg
            } else {
                imageView.setImageResource(R.drawable.p1) // Chuyển lại p1.jpg
            }
            isImageP1 = !isImageP1 // Chuyển đổi trạng thái của isImageP1
        }

        // Khởi tạo flashcards
        flashcards = createFlashcards()

        // Thiết lập sự kiện cho nút tăng
        val incrementButton = findViewById<Button>(R.id.button_increment)
        incrementButton.setOnClickListener {
            // Hiển thị phép nhân tiếp theo ngay lập tức
            showNextMultiplication(multiplicationTableText)

            // Chuyển đổi giữa hình ảnh p1.jpg và p2.jpg
            if (isImageP1) {
                imageView.setImageResource(R.drawable.p2) // Chuyển sang p2.jpg
            } else {
                imageView.setImageResource(R.drawable.p1) // Chuyển lại p1.jpg
            }
            isImageP1 = !isImageP1 // Chuyển đổi trạng thái của isImageP1
        }

        // Thiết lập sự kiện nhấn lâu để hiển thị bảng nhân nhanh hơn
        incrementButton.setOnLongClickListener {
            isLongPressing = true
            val runnable = object : Runnable {
                override fun run() {
                    if (isLongPressing) {
                        // Hiển thị phép nhân tiếp theo ngay lập tức
                        showNextMultiplication(multiplicationTableText)

                        // Chuyển đổi giữa hình ảnh p1.jpg và p2.jpg
                        if (isImageP1) {
                            imageView.setImageResource(R.drawable.p2) // Chuyển sang p2.jpg
                        } else {
                            imageView.setImageResource(R.drawable.p1) // Chuyển lại p1.jpg
                        }
                        isImageP1 = !isImageP1 // Chuyển đổi trạng thái của isImageP1
                        handler.postDelayed(this, 500) // Độ trễ 500ms giữa các lần tăng
                    }
                }
            }
            handler.post(runnable) // Bắt đầu chạy
            true // Chỉ định rằng sự kiện đã được xử lý
        }

        incrementButton.setOnTouchListener { _, event ->
            when (event.action) {
                // Khi người dùng thả nút, dừng việc tăng
                android.view.MotionEvent.ACTION_UP -> {
                    isLongPressing = false
                    handler.removeCallbacksAndMessages(null) // Dừng handler
                }
            }
            false // Trả về false để cho phép xử lý sự kiện bình thường
        }
    }
    fun goToFunMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, FunActivity::class.java)
        startActivity(intent)
    }
    private fun createFlashcards(): List<Flashcard> {
        // Tạo danh sách flashcards
        val flashcardsList = mutableListOf<Flashcard>()
        for (i in 1..9) {
            flashcardsList.add(Flashcard("$i x 1", "$i"))
            flashcardsList.add(Flashcard("$i x 2", "${i * 2}"))
            flashcardsList.add(Flashcard("$i x 3", "${i * 3}"))
            flashcardsList.add(Flashcard("$i x 4", "${i * 4}"))
            flashcardsList.add(Flashcard("$i x 5", "${i * 5}"))
            flashcardsList.add(Flashcard("$i x 6", "${i * 6}"))
            flashcardsList.add(Flashcard("$i x 7", "${i * 7}"))
            flashcardsList.add(Flashcard("$i x 8", "${i * 8}"))
            flashcardsList.add(Flashcard("$i x 9", "${i * 9}"))
        }
        return flashcardsList
    }

    private fun showNextMultiplication(multiplicationTableText: TextView) {
        val result = "$multiplicationIndex x ${currentIndex + 1} = ${multiplicationIndex * (currentIndex + 1)}"
        multiplicationTableText.text = result // Hiển thị kết quả bảng nhân

        currentIndex = (currentIndex + 1) % 9 // Tăng chỉ số
        if (currentIndex == 0) {
            multiplicationIndex++ // Tăng số nhân sau 9
        }
    }
}
