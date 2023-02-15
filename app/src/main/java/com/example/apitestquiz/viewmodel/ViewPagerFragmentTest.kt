package com.example.apitestquiz.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.apitestquiz.R
import com.example.apitestquiz.adapter.MyAdapterFragment
import com.example.apitestquiz.data.Retro
import com.example.apitestquiz.fragment.FragmentCommunicator
import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.network.QuestionApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewPagerFragmentTest : AppCompatActivity(),FragmentCommunicator {

    private var type:String = "science"
    private lateinit var listA : List<QuestionModelItem>
    private lateinit var pager: ViewPager2
    private lateinit var context:Context

    private lateinit var countQ:IntArray
    private lateinit var scoreQ:IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_fragment_test)
        pager = findViewById(R.id.view_pager2_fragment)

        context = this
        type= intent.getStringExtra("randomType") !!

        getQuestionCoroutine()

    }

    private fun getQuestionCoroutine() {
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        val handler = CoroutineExceptionHandler { _, throwable ->
            Toast.makeText(this@ViewPagerFragmentTest,"$throwable", Toast.LENGTH_LONG).show()
        }
        lifecycleScope.launch(Dispatchers.Main+handler) {
            launch {
                val response = retro.getQuestionCat(type)
                if(response.isSuccessful){
                    listA= response.body()!!
                    scoreQ= IntArray(listA.size)
                    countQ= IntArray(listA.size)
                    val adapter= MyAdapterFragment(context as AppCompatActivity,listA)
                    this@ViewPagerFragmentTest.pager.adapter = adapter
                }
            }
        }
    }

    override fun onDataPass(score: Int, count: Int,position:Int) {
        scoreQ[position] = score
        countQ[position] = count
    }

    override fun onPagePass(page: Int) {
        pager.currentItem = page+1
        if (page==4){
            if(countQ.sum()==5){
                val intent = Intent (context, EndActivity::class.java)
                intent.putExtra("scoreFin",scoreQ.sum().toString())
                context.startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Please answer all question !",Toast.LENGTH_SHORT).show()
            }
        }
    }
}