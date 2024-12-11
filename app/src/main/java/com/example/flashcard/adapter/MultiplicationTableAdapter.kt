package com.example.flashcard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard.R

class MultiplicationTableAdapter(private val context: Context) :
    RecyclerView.Adapter<MultiplicationTableAdapter.ViewHolder>() {

    private val backgrounds = listOf(
        R.drawable.c1, R.drawable.c2, R.drawable.c3,
        R.drawable.c4, R.drawable.c5, R.drawable.c6,
        R.drawable.c7, R.drawable.c8, R.drawable.c9
    )

    private val tables = listOf(
        generateMultiplicationTable(1),
        generateMultiplicationTable(2),
        generateMultiplicationTable(3),
        generateMultiplicationTable(4),
        generateMultiplicationTable(5),
        generateMultiplicationTable(6),
        generateMultiplicationTable(7),
        generateMultiplicationTable(8),
        generateMultiplicationTable(9)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.page_multiplication_table, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.backgroundImage.setImageResource(backgrounds[position])
        holder.multiplicationText.text = tables[position]
    }

    override fun getItemCount(): Int {
        return backgrounds.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val backgroundImage: ImageView = itemView.findViewById(R.id.backgroundImage)
        val multiplicationText: TextView = itemView.findViewById(R.id.multiplicationText)
    }

    private fun generateMultiplicationTable(number: Int): String {
        val builder = StringBuilder()
        for (i in 1..9) {
            builder.append("$number x $i = ${number * i}\n")
        }
        return builder.toString()
    }
}
