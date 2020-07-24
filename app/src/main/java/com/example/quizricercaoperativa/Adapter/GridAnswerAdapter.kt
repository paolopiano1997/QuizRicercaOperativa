package com.example.quizricercaoperativa.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizricercaoperativa.Common.Common
import com.example.quizricercaoperativa.Model.CurrentQuestion
import com.example.quizricercaoperativa.R

class GridAnswerAdapter(internal var context: Context,
                        internal var answerSheetList: List<CurrentQuestion>):
    RecyclerView.Adapter<GridAnswerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var question_item : View
        init{
            question_item = itemView.findViewById(R.id.question_item) as View
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_grid_answer_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        println( " AnswerSheetSize: ${answerSheetList.size}")
        return answerSheetList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        println("Position bind view: $position")
        when(answerSheetList[position].type) {
            Common.ANSWER_TYPE.RIGHT_ANSWER ->  holder.question_item.setBackgroundResource(R.drawable.grid_item_right_answer)
            Common.ANSWER_TYPE.WRONG_ANSWER -> holder.question_item.setBackgroundResource(R.drawable.grid_item_wrong_answer)
            else -> holder.question_item.setBackgroundResource(R.drawable.grid_item_no_answer)
        }
    }
}