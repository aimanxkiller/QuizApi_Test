package com.example.apitestquiz.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.apitestquiz.R
import com.example.apitestquiz.data.Retro
import com.example.apitestquiz.model.QuestionModelItem
import com.example.apitestquiz.network.QuestionApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ViewPagerFragmentTest : AppCompatActivity() {

    private var type:String = "science"
    private lateinit var listA : List<QuestionModelItem>
    private lateinit var view_pager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_fragment_test)

        //Fragment begins
        var pager = findViewById<ViewPager2>(R.id.view_pager2_fragment)
//        pager.adapter = MyAdapterFragment(supportFragmentManager,lifecycle)

        type= intent.getStringExtra("randomType") !!

        getQuestionCoroutine()

    }

    private fun getQuestionCoroutine(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        val handler = CoroutineExceptionHandler { _, throwable ->
            Toast.makeText(this@ViewPagerFragmentTest,"No internet connection : $throwable", Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch(Dispatchers.Main+handler) {
            val x = async {
                val response = retro.getQuestionCat(type)
                if(response.isSuccessful){
                    listA= response.body()!!
                }
                return@async listA
            }

            //inflate
//          view_pager2.adapter = ViewPagerAdapter(x.await())
        }
    }
}