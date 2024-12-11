package com.example.flashcard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard.R
import com.example.flashcard.data.Question

class QuestionAdapter(
    private val questionList: List<Question>,
    private val onDeleteClick: (Question) -> Unit // Lambda function to handle delete
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]
        holder.questionTextView.text = question.question
        holder.answerTextView.text = question.answer

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClick(question)
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton) // Reference to delete button
    }
}
