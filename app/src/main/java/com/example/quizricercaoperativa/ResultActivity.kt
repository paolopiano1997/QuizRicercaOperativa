package com.example.quizricercaoperativa

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizricercaoperativa.Adapter.ResultGridAdapter
import com.example.quizricercaoperativa.Common.Common
import com.example.quizricercaoperativa.Common.SpacesItemDecoration
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.txt_right_answer
import kotlinx.android.synthetic.main.content_question.*
import java.util.concurrent.TimeUnit

class ResultActivity : AppCompatActivity() {

    internal var backToQuestion : BroadcastReceiver = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.action!!.toString() == Common.KEY_BACK_FROM_RESULT){
                val questionindex = intent.getIntExtra(Common.KEY_BACK_FROM_RESULT, -1)
                goBackActivityWithQuestionIndex(questionindex)
            }
        }
    }

    private fun goBackActivityWithQuestionIndex(questionindex: Int) {
        val returnIntent = Intent()
        returnIntent.putExtra(Common.KEY_BACK_FROM_RESULT,questionindex)
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.menu_do_quiz_again -> doQuizAgain()
            R.id.menu_view_answer -> viewAnswer()
            android.R.id.home -> {
                val intent = Intent(applicationContext,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent) //GO back to block when click on back arrow on result activity
            }
        }
        return true
    }

    private fun viewAnswer(){
        val returnIntent = Intent()
        returnIntent.putExtra("action","viewanswer")
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }

    private fun doQuizAgain(){
        MaterialStyledDialog.Builder(this@ResultActivity)
            .setTitle("Vuoi fare di nuovo il quiz")
            .setDescription("Sei sicuro di voler riniziare il quiz?")
            .setIcon(R.drawable.ic_mood_white_24dp)
            .setNegativeText("No")
            .onNegative{dialog,which ->
                dialog.dismiss()
            }
            .setPositiveText("Sì")
            .onPositive { dialog, which ->
                val returnIntent = Intent()
                returnIntent.putExtra("action","doquizagain")
                setResult(Activity.RESULT_OK,returnIntent)
                finish()
            }.show()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this@ResultActivity)
            .unregisterReceiver(backToQuestion)
        super.onDestroy()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        LocalBroadcastManager.getInstance(this@ResultActivity)
            .registerReceiver(backToQuestion, IntentFilter(Common.KEY_BACK_FROM_RESULT))


        toolbar.title = "RISULTATI"

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //
        txt_time.text = (
                java.lang.String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(Common.timer.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(Common.timer.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Common.timer.toLong()))))

        txt_right_answer.text = "${Common.right_answer_count}/${Common.questionList.size}"
        btn_filter_total.text = "${Common.questionList.size}"
        btn_filter_right.text = "${Common.right_answer_count}"
        btn_filter_wrong.text = "${Common.wrong_answer_count}"
        btn_filter_no_answer.text = "${Common.no_answer_count}"

        val res = Common.right_answer_count * 2
        when{
            res>=18 -> txt_result.text = "${res}"
            res<18 -> txt_result.text = "Bosciato"
        }
        //Event button
        btn_filter_total.setOnClickListener {
            val adapter = ResultGridAdapter(this,Common.answerSheetList)
            recycler_result.adapter = adapter
        }

        btn_filter_no_answer.setOnClickListener {
            Common.answerSheetListFiltered.clear()
            for(cq in Common.answerSheetList)
                if(cq.type == Common.ANSWER_TYPE.NO_ANSWER)
                    Common.answerSheetListFiltered.add(cq)
            val adapter = ResultGridAdapter(this,Common.answerSheetListFiltered)
            recycler_result.adapter = adapter
        }

        btn_filter_wrong.setOnClickListener {
            Common.answerSheetListFiltered.clear()
            for(cq in Common.answerSheetList)
                if(cq.type == Common.ANSWER_TYPE.WRONG_ANSWER)
                    Common.answerSheetListFiltered.add(cq)
            val adapter = ResultGridAdapter(this,Common.answerSheetListFiltered)
            recycler_result.adapter = adapter
        }

        btn_filter_right.setOnClickListener {
            Common.answerSheetListFiltered.clear()
            for(cq in Common.answerSheetList)
                if(cq.type == Common.ANSWER_TYPE.RIGHT_ANSWER)
                    Common.answerSheetListFiltered.add(cq)
            val adapter = ResultGridAdapter(this,Common.answerSheetListFiltered)
            recycler_result.adapter = adapter
        }



        //Set adapter
        val adapter = ResultGridAdapter(this,Common.answerSheetList)
        recycler_result.setHasFixedSize(true)
        recycler_result.layoutManager = GridLayoutManager(this,4)
        recycler_result.addItemDecoration(SpacesItemDecoration(4))
        recycler_result.adapter = adapter

    }
}
