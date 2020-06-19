package com.example.quizricercaoperativa

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.quizricercaoperativa.Common.Common
import com.example.quizricercaoperativa.Interface.IAnswerSelect
import com.example.quizricercaoperativa.Model.CurrentQuestion
import com.example.quizricercaoperativa.Model.Question

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : Fragment(), IAnswerSelect{

    lateinit var txt_question_text: TextView
    lateinit var ckb_A: CheckBox
    lateinit var ckb_B: CheckBox
    lateinit var ckb_C: CheckBox
    lateinit var ckb_D: CheckBox
    lateinit var ckb_E: CheckBox

    lateinit var layout_image: FrameLayout
    lateinit var progress_bar: ProgressBar

    var question: Question?=null
    var questionIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemView = inflater.inflate(R.layout.fragment_question,container,false)
        questionIndex = arguments!!.getInt("index",-1)
       // layout_image = itemView.findViewById(R.id.layout_image) as FrameLayout
        question = Common.questionList[questionIndex]
        if(question != null){
            //progress_bar = itemView.findViewById(R.id.progressBar) as ProgressBar
            txt_question_text = itemView.findViewById(R.id.txt_question_text) as TextView
            txt_question_text.text = question!!.questionText

            ckb_A = itemView.findViewById(R.id.ckb_A)
            ckb_A.text = question!!.answerA
            ckb_B = itemView.findViewById(R.id.ckb_B)
            ckb_B.text = question!!.answerB
            ckb_C = itemView.findViewById(R.id.ckb_C)
            ckb_C.text = question!!.answerC
            ckb_D = itemView.findViewById(R.id.ckb_D)
            ckb_D.text = question!!.answerD
            ckb_E = itemView.findViewById(R.id.ckb_E)
            ckb_E.text = question!!.answerE

            ckb_A.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                    Common.selected_values.add(ckb_A.text.toString())
                else
                    Common.selected_values.remove(ckb_A.text.toString())
             }
            ckb_B.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                    Common.selected_values.add(ckb_B.text.toString())
                else
                    Common.selected_values.remove(ckb_B.text.toString())
            }
            ckb_C.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                    Common.selected_values.add(ckb_C.text.toString())
                else
                    Common.selected_values.remove(ckb_C.text.toString())
            }
            ckb_D.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                    Common.selected_values.add(ckb_D.text.toString())
                else
                    Common.selected_values.remove(ckb_D.text.toString())
            }
            ckb_E.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                    Common.selected_values.add(ckb_E.text.toString())
                else
                    Common.selected_values.remove(ckb_E.text.toString())
            }
        }
        return itemView
    }

    override fun selectedAnswer(): CurrentQuestion {
        //rimuovi duplicati
        Common.selected_values.distinct()
        Common.selected_values.sort()
        if(Common.answerSheetList[questionIndex].type == Common.ANSWER_TYPE.NO_ANSWER){
            val currentQuestion = CurrentQuestion(questionIndex, Common.ANSWER_TYPE.NO_ANSWER)
            val result = StringBuilder()
            if(Common.selected_values.size > 1){
                val arrayNumber = Common.selected_values.toTypedArray()
                for(i in arrayNumber!!.indices){
                    if(i<arrayNumber!!.size -1)
                        result.append(StringBuilder((arrayNumber!![i] as String).substring(0,1)).append(","))
                    else
                        result.append((arrayNumber!![i] as String).substring(0,1))
                }
            }
            else if (Common.selected_values.size==1){
                val arrayAnswer = Common.selected_values.toTypedArray()
                result.append((arrayAnswer!![0] as String).substring(0,1))
            }
            if(question != null){
                if(!TextUtils.isEmpty(result)){
                    if(result.toString()==question!!.correctAnswer)
                        currentQuestion.type = Common.ANSWER_TYPE.RIGHT_ANSWER
                    else
                        currentQuestion.type = Common.ANSWER_TYPE.WRONG_ANSWER
                }
                else
                    currentQuestion.type=Common.ANSWER_TYPE.NO_ANSWER
            }else
            {
                Toast.makeText(activity,"Non è possibile prendere la domanda", Toast.LENGTH_SHORT).show()
                currentQuestion.type=Common.ANSWER_TYPE.NO_ANSWER
            }
            Common.selected_values.clear()
            return currentQuestion
        }
        else
            return Common.answerSheetList[questionIndex]
    }

    override fun showCorrectAnswer() {
        val correctAnswers = question!!.correctAnswer!!.split(",".toRegex())
            .dropLastWhile{it.isEmpty()}
        for(answer in correctAnswers){
            when (answer){
                "A" -> {
                    ckb_A.setTypeface(null, Typeface.BOLD)
                    ckb_A.setTextColor(Color.RED)
                }
                "B" -> {
                    ckb_B.setTypeface(null, Typeface.BOLD)
                    ckb_B.setTextColor(Color.RED)
                }
                "C" -> {
                    ckb_C.setTypeface(null, Typeface.BOLD)
                    ckb_C.setTextColor(Color.RED)
                }
                "D" -> {
                    ckb_D.setTypeface(null, Typeface.BOLD)
                    ckb_D.setTextColor(Color.RED)
                }
                "E" -> {
                    ckb_E.setTypeface(null, Typeface.BOLD)
                    ckb_E.setTextColor(Color.RED)
                }
            }
        }
    }

    override fun disableAnswer() {
        ckb_A.isEnabled = false
        ckb_B.isEnabled = false
        ckb_C.isEnabled = false
        ckb_D.isEnabled = false
        ckb_E.isEnabled = false
    }

    override fun resetQuestion() {
        ckb_A.isEnabled = true
        ckb_B.isEnabled = true
        ckb_C.isEnabled = true
        ckb_D.isEnabled = true
        ckb_E.isEnabled = true

        ckb_A.isChecked = false
        ckb_B.isChecked = false
        ckb_C.isChecked = false
        ckb_D.isChecked = false
        ckb_E.isChecked = false

        ckb_A.setTypeface(null, Typeface.NORMAL)
        ckb_A.setTextColor(Color.BLACK)
        ckb_B.setTypeface(null, Typeface.NORMAL)
        ckb_B.setTextColor(Color.BLACK)
        ckb_C.setTypeface(null, Typeface.NORMAL)
        ckb_C.setTextColor(Color.BLACK)
        ckb_D.setTypeface(null, Typeface.NORMAL)
        ckb_D.setTextColor(Color.BLACK)
        ckb_E.setTypeface(null, Typeface.NORMAL)
        ckb_E.setTextColor(Color.BLACK)

        Common.selected_values.clear()
    }


}
