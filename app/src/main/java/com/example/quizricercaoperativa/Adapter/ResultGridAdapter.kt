package com.example.quizricercaoperativa.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizricercaoperativa.Common.Common
import com.example.quizricercaoperativa.Model.CurrentQuestion
import com.example.quizricercaoperativa.R

class ResultGridAdapter(internal var context: Context,
                        internal var answerSheetList: List<CurrentQuestion>
                        ) : RecyclerView.Adapter<ResultGridAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        internal var btn_question_num: Button
        init{
            btn_question_num = itemView.findViewById(R.id.btn_question)
            btn_question_num.setOnClickListener{
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_BACK_FROM_RESULT).putExtra(
                        Common.KEY_BACK_FROM_RESULT,answerSheetList[adapterPosition].questionIndex
                    ))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_result_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.btn_question_num.text = StringBuilder("Domanda ").append(answerSheetList[position].questionIndex + 1)
        when(answerSheetList[position].type){
            Common.ANSWER_TYPE.RIGHT_ANSWER -> {
                holder.btn_question_num.setBackgroundResource(R.drawable.grid_item_right_answer)
                val img = context.resources.getDrawable(R.drawable.ic_check_white_24dp)
                holder.btn_question_num.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, img)
            }
            Common.ANSWER_TYPE.WRONG_ANSWER -> {
                holder.btn_question_num.setBackgroundResource(R.drawable.grid_item_wrong_answer)
                val img = context.resources.getDrawable(R.drawable.ic_clear_white_24dp)
                holder.btn_question_num.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, img)
            }
            else -> {
                holder.btn_question_num.setBackgroundResource(R.drawable.grid_item_no_answer)
                val img = context.resources.getDrawable(R.drawable.ic_error_outline_white_24dp)
                holder.btn_question_num.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, null, img)
            }
        }
    }

    override fun getItemCount(): Int {
       return answerSheetList.size
    }


}