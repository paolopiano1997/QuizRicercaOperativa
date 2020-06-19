package com.example.quizricercaoperativa.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizricercaoperativa.Common.Common
import com.example.quizricercaoperativa.Interface.IOnRecyclerViewItemClickListener
import com.example.quizricercaoperativa.Model.CurrentQuestion
import com.example.quizricercaoperativa.R

class QuestionListHelperAdapter(internal var context: Context,
                                internal var answerSheetList: List<CurrentQuestion>
                                ): RecyclerView.Adapter<QuestionListHelperAdapter.MyViewHolder>() {
    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        override fun onClick(v: View?) {
            iOnRecyclerViewItemClickListener.onClick(v!!,adapterPosition)
        }
        internal var txt_question_num: TextView
        internal var layout_wrapper : LinearLayout

        lateinit var  iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener

        fun setiOnRecyclerViewItemClickListener ( iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener){
            this.iOnRecyclerViewItemClickListener = iOnRecyclerViewItemClickListener
        }

        init {
            txt_question_num = itemView.findViewById(R.id.txt_question_num) as TextView
            layout_wrapper = itemView.findViewById(R.id.layout_wrapper) as LinearLayout
            itemView.setOnClickListener(this)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_question_list_helper_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return answerSheetList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_question_num.text = (position + 1).toString()
        when (answerSheetList[position].type) {
            Common.ANSWER_TYPE.RIGHT_ANSWER ->
                holder.layout_wrapper.setBackgroundResource(R.drawable.grid_item_right_answer)
            Common.ANSWER_TYPE.WRONG_ANSWER ->
                holder.layout_wrapper.setBackgroundResource(R.drawable.grid_item_wrong_answer)
            else ->
                holder.layout_wrapper.setBackgroundResource(R.drawable.grid_item_no_answer)
        }
        holder.setiOnRecyclerViewItemClickListener(object : IOnRecyclerViewItemClickListener {
            override fun onClick(view: View, position: Int) {
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(
                        Intent(Common.KEY_GO_TO_QUESTION).putExtra(
                            Common.KEY_GO_TO_QUESTION,
                            position
                        )
                    )
            }
        })
    }
}