@file:Suppress("DEPRECATION")

package com.example.apitestquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var textView: TextView
    private lateinit var buttonNext: Button

    lateinit var listA : List<QuestionModelItem>
    private var answerTrue:String = "a"
    private var answerChoice:String = "b"
    private var count = 0 //counting questions
    private var score = 0 //using for scores
    private var radioId:Int = -1
    private var type:String = "science"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.textQuestion)
        buttonNext = findViewById(R.id.buttonNext)

        //updated to use internal failure from retrofit for no internet connection
        type = intent.getStringExtra("randomType")!!
        getQuestion()
        buttonNext.setOnClickListener {
            buttonClick()
        }
    }

    private fun buttonClick(){
        if(radioId == -1){
            Toast.makeText(this,"Please select an answer  !",Toast.LENGTH_SHORT).show()
        }else{
            radioId = radioGroup.checkedRadioButtonId
            radioButton = findViewById(radioId)
            answerChoice = radioButton.text.toString()
            score += checkAnswer(answerChoice,answerTrue)
            if (count<listA.size){
                nextQuestion()
                radioId = -1
                radioGroup.clearCheck()
            }else {
                val intent = Intent(this@MainActivity,EndActivity::class.java)
                Log.e("Ending", "The $score is total")
                intent.putExtra("scoreFin",score.toString())
                startActivity(intent)
            }
        }
    }

    private fun retryConnection(){
        val intent2 = Intent(this,MainActivity::class.java)
        startActivity(intent2)
    }

    private fun getQuestion() {
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        retro.getQuestionCat(type).enqueue(object :Callback<List<QuestionModelItem>>{
            override fun onResponse(
                call: Call<List<QuestionModelItem>>,
                response: Response <List<QuestionModelItem>>
            ) {
                //Changed to using okHTTP logging
                listA = response.body()!!
                nextQuestion()
            }
            //updated using onFailure and placing retry option within onFailure
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<List<QuestionModelItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"No Network Connection",Toast.LENGTH_SHORT).show()

                buttonNext.text ="Retry"
                buttonNext.setOnClickListener {
                    retryConnection()
                }
                Log.e("Fail","Failed to get data")
            }
        })
    }


    @SuppressLint("SetTextI18n")
    fun nextQuestion(){
        textView.text = listA[count].question //Questions
        val answerCorrect = listA[count].correctAnswer //Getting answer from API
        val answerWrong:List<String> = listA[count].incorrectAnswers
        val answerCollect = answerWrong + answerCorrect  //Getting answer collections and shuffling
        val answerShuffle = answerCollect.toMutableList()
        answerShuffle.shuffle()

        /*for (i in 0 until radioGroup.childCount){ //output of answers to radioButton text
            radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.text = answerShuffle[i]
        }*/ //update here

        //Try using forEach/forEachIndexed if looping with index
        radioGroup.children.forEachIndexed { index, view ->
            radioButton = view as RadioButton
            radioButton.text = answerShuffle[index]
        }
        answerTrue = answerCorrect
        count++
        if(count==5)
            buttonNext.text = ("Submit")
    }

    private fun checkAnswer(a:String, b:String):Int{
        return if (a.equals(b,true))
            1
        else
            0
    }

    @Suppress("UNUSED_PARAMETER")
    fun checkButton(view: View){ //Checking button click
        radioId = radioGroup.checkedRadioButtonId
        radioButton = findViewById(radioId)
        answerChoice = radioButton.text.toString()
    }

}


