package com.example.flashcard.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "task.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "questions"
        const val COLUMN_ID = "id"
        const val COLUMN_QUESTION = "question"
        const val COLUMN_ANSWER = "answer"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_QUESTION TEXT, "
                + "$COLUMN_ANSWER TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Add a question to the database
    fun addQuestion(question: String, answer: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_QUESTION, question)
            put(COLUMN_ANSWER, answer)
        }
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return success // Return the inserted row ID or -1 if an error occurred
    }

    // Retrieve all questions from the database
    fun getAllQuestions(): List<Question> {
        val questionList = mutableListOf<Question>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val questionText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION))
                val answerText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER))
                questionList.add(Question(id, questionText, answerText))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return questionList
    }

    // Update an existing question
    fun updateQuestion(id: Int, question: String, answer: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_QUESTION, question)
            put(COLUMN_ANSWER, answer)
        }
        val rowsAffected = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return rowsAffected // Returns the number of rows affected
    }

    // Delete a question
    fun deleteQuestion(id: Int): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted // Returns the number of rows deleted
    }

    // Get a single question by ID
    fun getQuestionById(id: Int): Question? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_QUESTION, COLUMN_ANSWER),
            "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor?.moveToFirst()
        val question = if (cursor != null && cursor.count > 0) {
            Question(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER))
            )
        } else null
        cursor?.close()
        db.close()
        return question
    }

    // NEW: Get the total number of questions
    fun getQuestionCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }
    fun getRandomQuestions(count: Int): List<Question> {
        val questionList = mutableListOf<Question>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY RANDOM() LIMIT ?", arrayOf(count.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val questionText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION))
                val answerText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER))
                questionList.add(Question(id, questionText, answerText))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return questionList
    }

}