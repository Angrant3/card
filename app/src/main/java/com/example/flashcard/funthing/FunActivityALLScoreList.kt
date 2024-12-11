package com.example.flashcard.funthing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard.R
import com.example.flashcard.data.PlayerScore
import com.google.firebase.database.*
import com.example.flashcard.adapter.ScoreListAdapter



class FunActivityALLScoreList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScoreListAdapter
    private lateinit var tvCurrentPosition: TextView
    private val playerScoreList = mutableListOf<PlayerScore>()

    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("PlayerScores")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun_all_score_list)

        recyclerView = findViewById(R.id.recyclerViewScores)
        tvCurrentPosition = findViewById(R.id.tvCurrentPosition)

        adapter = ScoreListAdapter(playerScoreList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadScoresFromFirebase()
    }

    fun goToFunMain(view: View) {
        // Sử dụng Intent để chuyển sang MainActivity
        val intent = Intent(this, FunActivity::class.java)
        startActivity(intent)
    }

    private fun loadScoresFromFirebase() {
        databaseReference.orderByChild("score").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                playerScoreList.clear()
                var currentPosition = 0
                var currentPlayerName = "YourUsername" // Replace with the logged-in username

                snapshot.children.reversed().forEachIndexed { index, dataSnapshot ->
                    val score = dataSnapshot.getValue(PlayerScore::class.java)
                    score?.let {
                        playerScoreList.add(it.copy(stt = index + 1))
                        if (it.name == currentPlayerName) {
                            currentPosition = index + 1
                        }
                    }
                }

                adapter.notifyDataSetChanged()

                val currentPlayerScore = playerScoreList.find { it.name == currentPlayerName }
                tvCurrentPosition.text = "Vị trí của bạn: $currentPosition - Điểm: ${currentPlayerScore?.score ?: 0}"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}