package com.example.apitestquiz.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.apitestquiz.R
import com.example.apitestquiz.data.Retro
import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.network.QuestionApi
import kotlinx.coroutines.*


lateinit var pager : ViewPager2

class ViewPagerFragmentTest : AppCompatActivity() {

    private var type:String = "science"
    private lateinit var listA : List<QuestionModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_fragment_test)
        pager = findViewById(R.id.view_pager2_fragment)

        type= intent.getStringExtra("randomType") !!

        getQuestionCoroutine()

    }

    private fun getQuestionCoroutine(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        val handler = CoroutineExceptionHandler { _, throwable ->
            Toast.makeText(this@ViewPagerFragmentTest,"$throwable", Toast.LENGTH_LONG).show()
        }
        lifecycleScope.launch(Dispatchers.Main+handler) {
            async {
                val response = retro.getQuestionCat(type)
                if(response.isSuccessful){
                    listA= response.body()!!
                    val fragment = supportFragmentManager
                    delay(500)
                    pager.adapter = MyAdapterFragment(fragment,lifecycle,listA)
                }
            }
        }
    }

    fun getViewPager(): ViewPager2 {
        return pager
    }
}