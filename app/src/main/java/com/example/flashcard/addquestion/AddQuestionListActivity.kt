package com.example.flashcard.addquestion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard.data.DatabaseHelper
import com.example.flashcard.data.Question
import com.example.flashcard.adapter.QuestionAdapter
import com.example.flashcard.R

class AddQuestionListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private var questionList: MutableList<Question> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acvitity_add_question_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lấy dữ liệu từ cơ sở dữ liệu
        databaseHelper = DatabaseHelper(this)
        questionList = databaseHelper.getAllQuestions().toMutableList()

        // Cập nhật adapter cho RecyclerView với hành động xóa
        adapter = QuestionAdapter(questionList) { question ->
            deleteQuestion(question)
        }
        recyclerView.adapter = adapter
    }

    private fun deleteQuestion(question: Question) {
        // Xóa câu hỏi từ cơ sở dữ liệu
        val rowsDeleted = databaseHelper.deleteQuestion(question.id)
        if (rowsDeleted > 0) {
            // Xóa câu hỏi từ danh sách và cập nhật giao diện
            questionList.remove(question)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Câu hỏi đã được xóa", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Xóa không thành công", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToMainaddquestion(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, AddQuestionActivity::class.java)
        startActivity(intent)
    }

}
