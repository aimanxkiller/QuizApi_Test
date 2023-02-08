package com.example.apitestquiz.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.apitestquiz.network.QuestionApi
import com.example.apitestquiz.model.QuizCat
import com.example.apitestquiz.R
import com.example.apitestquiz.data.Retro
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import androidx.lifecycle.*
import retrofit2.*

class SelectionActivity : AppCompatActivity() {
    private lateinit var spinner:Spinner
    private lateinit var buttonNext:Button
    lateinit var selection:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        buttonNext = findViewById(R.id.buttonSel)
        spinner = findViewById(R.id.dropdownList)

        getQuizCatCoroutine()
//        getQuizCat()
        buttonNext.setOnClickListener {
            buttonClick()
        }
    }

    private fun spinnerEnabler(x: QuizCat){
        val categoryY = x.getTitle()
        val categoryX = x.getDetails()

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categoryX)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val y = categoryY[position].split(",")
                selection = y[0]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    //updated here using coroutine for API calling
    @SuppressLint("SetTextI18n")
    private fun getQuizCatCoroutine(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        val handler = CoroutineExceptionHandler { _, throwable ->
            Toast.makeText(this@SelectionActivity,"No internet connection : $throwable",Toast.LENGTH_SHORT).show()
            buttonNext.text ="Retry"
            buttonNext.setOnClickListener {
                retryConnection()
            }
        }
        lifecycleScope.launch(Dispatchers.Main+handler){
            val response = retro.getCategories().awaitResponse()
            if(response.isSuccessful){
                spinnerEnabler(response.body()!!)
            }
        }
    }

    private fun retryConnection(){
        val intent2 = Intent(this, SelectionActivity::class.java)
        startActivity(intent2)
        finish()
    }

//    private fun getQuizCat(){
//        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
//        retro.getCategories().enqueue(object : Callback<QuizCat>{
//            override fun onResponse(call: Call<QuizCat>, response: Response<QuizCat>){
//                if(response.isSuccessful){
//                    spinnerEnabler(response.body()!!)
//                }
//                else{
//                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<QuizCat>, t: Throwable) {
//                Toast.makeText(this@SelectionActivity,"Failed to get Data",Toast.LENGTH_SHORT).show()
//                Log.e("Fail","Failed to get data: "+ t.message)
//            }
//        })
//    }

    private fun buttonClick(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("randomType",selection)
        startActivity(intent)
        finish()
    }
}