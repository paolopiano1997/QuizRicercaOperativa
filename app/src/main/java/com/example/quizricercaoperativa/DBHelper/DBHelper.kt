package com.example.quizricercaoperativa.DBHelper

import android.content.Context
import com.example.quizricercaoperativa.Model.Block
import com.example.quizricercaoperativa.Model.Question
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import java.io.Console

class DBHelper(context: Context): SQLiteAssetHelper(context,DB_NAME, null, DB_VER) {
    companion object {
        private var instance: DBHelper? = null
        private val DB_NAME = "RicercaOperativa.db"
        private val DB_VER = 1
        @Synchronized
        fun getInstance(context: Context): DBHelper{
            if(instance==null)
                instance = DBHelper(context)
            return instance!!
        }
    }

    val allBlocks : MutableList<Block>
        get() {
            val db = instance!!.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM BLOCK", null)
            val blocks = ArrayList<Block>()
            if(cursor.moveToFirst())
                while(!cursor.isAfterLast) {
                    val block = Block(
                        cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("Name"))
                    )
                    blocks.add(block)
                    cursor.moveToNext()
                }
            cursor.close()
            db.close()
            return blocks
        }

    fun getQuestionByBlock(blockId:Int):MutableList<Question>{
        println("Reading questions...")
        val db = instance!!.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM QUESTION WHERE BlockId=$blockId ORDER BY RANDOM() LIMIT 16",null)
        val questionList = ArrayList<Question>()
        if(cursor.moveToFirst())
            while(!cursor.isAfterLast){
                val question = Question(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("QuestionText")),
                    cursor.getString(cursor.getColumnIndex("AnswerA")),
                    cursor.getString(cursor.getColumnIndex("AnswerB")),
                    cursor.getString(cursor.getColumnIndex("AnswerC")),
                    cursor.getString(cursor.getColumnIndex("AnswerD")),
                    cursor.getString(cursor.getColumnIndex("AnswerE")),
                    cursor.getString(cursor.getColumnIndex("CorrectAnswer")),
                    cursor.getInt(cursor.getColumnIndex("BlockId"))
                    )
                println("Read question: ${question.correctAnswer}, ${question.answerA}")
                questionList.add(question)
                cursor.moveToNext()
            }
        cursor.close()
        db.close()
        return questionList
    }
}