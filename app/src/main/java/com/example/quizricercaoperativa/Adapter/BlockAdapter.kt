package com.example.quizricercaoperativa.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizricercaoperativa.Common.Common
import com.example.quizricercaoperativa.Interface.IOnRecyclerViewItemClickListener
import com.example.quizricercaoperativa.Model.Block
import com.example.quizricercaoperativa.Model.Question
import com.example.quizricercaoperativa.QuestionActivity
import com.example.quizricercaoperativa.R
import kotlinx.android.synthetic.main.layout_block.view.*

class BlockAdapter(
    internal var context: Context,
    internal var blockList:List<Block>
) : RecyclerView.Adapter<BlockAdapter.MyViewHolder>(){


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        internal var txt_block_name : TextView
        internal var card_block : CardView
        internal lateinit var iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener

        fun setiOnRecyclerViewItemClickListener(iOnRecyclerViewItemClickListener : IOnRecyclerViewItemClickListener) {
            this.iOnRecyclerViewItemClickListener = iOnRecyclerViewItemClickListener
        }
        init {
            txt_block_name = itemView.findViewById(R.id.txt_block_name) as TextView
            card_block = itemView.findViewById(R.id.card_block) as CardView
            itemView.setOnClickListener(this)
        }
        override fun onClick(view : View){
            iOnRecyclerViewItemClickListener.onClick(view,adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_block,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = blockList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_block_name.text = blockList[position].name
        holder.setiOnRecyclerViewItemClickListener(object:IOnRecyclerViewItemClickListener{
            override fun onClick(view: View, position: Int) {
                Common.selectedBlock = blockList[position]
                val intent = Intent(context, QuestionActivity::class.java)
                context.startActivity(intent)

            }
        })
    }
}