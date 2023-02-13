package com.example.apitestquiz.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.apitestquiz.FragmentFirstPage
import com.example.apitestquiz.FragmentLastPage
import com.example.apitestquiz.FragmentMidPage
import com.example.apitestquiz.R
import com.example.apitestquiz.data.Retro
import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.network.QuestionApi
import kotlinx.coroutines.*

class ViewPagerFragmentTest : AppCompatActivity() {

    private var type:String = "science"
    private lateinit var listA : List<QuestionModelItem>
    private lateinit var pager: ViewPager2
    private lateinit var context:Context

    private lateinit var buttonL:Button
    private lateinit var buttonR:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_fragment_test)
        pager = findViewById(R.id.view_pager2_fragment)
        buttonL = findViewById(R.id.buttonLeft)
        buttonR = findViewById(R.id.buttonRight)

        context = this

        type= intent.getStringExtra("randomType") !!

        getQuestionCoroutine()

        setButtons()

    }

    private fun setButtons(){
        buttonR.setOnClickListener {
            pager.currentItem = pager.currentItem + 1
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
                    val adapter=MyAdapterFragment(context as AppCompatActivity,listA)
                    pager.adapter = adapter
                }
            }
        }

    }

}