package com.example.apitestquiz.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apitestquiz.R
import com.example.apitestquiz.data.Retro
import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.network.QuestionApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewHolder : AppCompatActivity() {

    private var type:String = "science"
    private lateinit var listA : List<QuestionModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_holder)

        type= intent.getStringExtra("randomType") !!
        var view_pager2:RecyclerView = findViewById(R.id.view_pager2)

        getQuestionCoroutine()

        view_pager2.adapter = ViewPagerAdapter(listA)
    }

    private fun getQuestionCoroutine(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        val handler = CoroutineExceptionHandler { _, throwable ->
            Toast.makeText(this@ViewHolder,"No internet connection : $throwable", Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch(Dispatchers.Main+handler){
            val response = retro.getQuestionCat(type)
            if(response.isSuccessful){
                listA= response.body()!!
                Log.e("Size", listA.size.toString())
            }
        }
    }

}