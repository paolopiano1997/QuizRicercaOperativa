package com.example.quizricercaoperativa.Interface

import com.example.quizricercaoperativa.Model.CurrentQuestion

interface IAnswerSelect {
    fun selectedAnswer():CurrentQuestion
    fun showCorrectAnswer()
    fun disableAnswer()
    fun resetQuestion()
}