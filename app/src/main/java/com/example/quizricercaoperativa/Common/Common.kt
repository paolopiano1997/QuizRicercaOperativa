package com.example.quizricercaoperativa.Common

import com.example.quizricercaoperativa.Model.Block
import com.example.quizricercaoperativa.Model.CurrentQuestion
import com.example.quizricercaoperativa.Model.Question
import com.example.quizricercaoperativa.QuestionFragment

object Common {

    val KEY_BACK_FROM_RESULT: String? = "back_from_result"
    val KEY_GO_TO_QUESTION: String? = "position_go_to"
    val TOTAL_TIME = 16*60*1000 //15 min

    var answerSheetListFiltered: MutableList<CurrentQuestion> = ArrayList()
    var answerSheetList: MutableList<CurrentQuestion> = ArrayList()
    var questionList: MutableList<Question> = ArrayList()
    var selectedBlock: Block?=null

    var fragmentList: MutableList<QuestionFragment> = ArrayList()

    var selected_values : MutableList<String> = ArrayList()

    var timer = 0
    var right_answer_count = 0
    var wrong_answer_count = 0
    var no_answer_count = 0
    var data_question = StringBuilder()

    enum class ANSWER_TYPE{
        NO_ANSWER,
        RIGHT_ANSWER,
        WRONG_ANSWER
    }
}