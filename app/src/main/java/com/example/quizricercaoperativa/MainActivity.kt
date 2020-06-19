package com.example.quizricercaoperativa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizricercaoperativa.Adapter.BlockAdapter
import com.example.quizricercaoperativa.Common.SpacesItemDecoration
import com.example.quizricercaoperativa.DBHelper.DBHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "RICERCA OPERATIVA QUIZ ONLINE"
        setSupportActionBar(toolbar)
        recycler_block.setHasFixedSize(true)
        recycler_block.layoutManager = GridLayoutManager(this,2)

        val adapter = BlockAdapter(this,DBHelper.getInstance(this).allBlocks)
        recycler_block.addItemDecoration(SpacesItemDecoration(4))
        recycler_block.adapter = adapter
    }
}
