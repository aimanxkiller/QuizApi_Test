package com.example.apitestquiz.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.apitestquiz.*
import com.example.apitestquiz.data.Retro
import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.network.QuestionApi
import kotlinx.coroutines.*

class ViewPagerFragmentTest : AppCompatActivity(),FragmentCommunicator {

    private var type:String = "science"
    private lateinit var listA : List<QuestionModelItem>
    private lateinit var pager: ViewPager2
    private lateinit var context:Context

    private lateinit var buttonL:Button
    private lateinit var buttonR:Button

    lateinit var countQ:IntArray
    lateinit var scoreQ:IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_fragment_test)
        pager = findViewById(R.id.view_pager2_fragment)
        buttonL = findViewById(R.id.buttonLeft)
        buttonR = findViewById(R.id.buttonRight)

        context = this

        type= intent.getStringExtra("randomType") !!

        getQuestionCoroutine()

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setButtons(position)
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setButtons(position: Int) {
        buttonR.text = "Next"
        buttonL.text = "Previous"
        buttonR.setOnClickListener {
            pager.currentItem = pager.currentItem + 1
        }
        when (position){
            0 ->{
                buttonL.visibility = View.INVISIBLE
            }
            (listA.size - 1) ->{
                buttonR.text = "Finish"
                buttonL.setOnClickListener {
                    pager.currentItem = pager.currentItem - 1
                }
                buttonR.setOnClickListener {
                    val intent = Intent (context, EndActivity::class.java)
                    intent.putExtra("scoreFin",scoreQ.sum().toString())
                    context.startActivity(intent)
                }
            }
            else ->{
                buttonL.visibility = View.VISIBLE
                buttonL.setOnClickListener {
                    pager.currentItem = pager.currentItem - 1
                }
            }
        }
    }

    private fun getQuestionCoroutine(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        val handler = CoroutineExceptionHandler { _, throwable ->
            Toast.makeText(this@ViewPagerFragmentTest,"$throwable", Toast.LENGTH_LONG).show()
        }
        lifecycleScope.launch(Dispatchers.Main+handler) {
            launch {
                val response = retro.getQuestionCat(type)
                if(response.isSuccessful){
                    listA= response.body()!!
                    delay(500)
                    scoreQ= IntArray(listA.size)
                    countQ= IntArray(listA.size)
                    val adapter=MyAdapterFragment(context as AppCompatActivity,listA)
                    pager.adapter = adapter
                }
            }
        }
    }

    override fun onDataPass(score: Int, count: Int,position:Int) {
        scoreQ[position] = score
        countQ[position] = count

        setButtons(position)
    }

}