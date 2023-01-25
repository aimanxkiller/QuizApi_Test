package com.example.apitestquiz

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var textView: TextView
    lateinit var buttonNext: Button

    lateinit var listA : List<QuestionModelItem>
    var answerTrue:String = "a"
    var answerChoice:String = "b"
    var count = 0 //counting questions
    var score = 0 //using for scores
    var radioId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.textQuestion)
        buttonNext = findViewById(R.id.buttonNext)


        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? =connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if(isConnected){
            getQuestion()

            buttonNext.setOnClickListener {

                if(radioId == -1){
                    Toast.makeText(this,"Please select an answer  !",Toast.LENGTH_SHORT).show()
                }else{
                    radioId = radioGroup.checkedRadioButtonId
                    radioButton = findViewById(radioId)
                    answerChoice = radioButton.text.toString()
                    score += checkAnswer(answerChoice,answerTrue)
                    Log.e("wow", "$score is total")
//                  Toast.makeText(this,"Score is $score /5",Toast.LENGTH_SHORT).show()
                    if (count<5){
                        nextQuestion(count)
                        radioId = -1
                        radioGroup.clearCheck()
                    }else {
                        var intent = Intent(this@MainActivity,EndActivity::class.java)
                        Log.e("Ending", "The $score is total")
                        intent.putExtra("scoreFin",score.toString())
                        startActivity(intent)
                    }
                }
            }
            
        }else{
            Toast.makeText(this,"No Network Connection",Toast.LENGTH_SHORT).show()
            buttonNext.text = "Retry"
            buttonNext.setOnClickListener {
                retryConnection()
            }
        }

    }

    fun retryConnection(){
        var intent2 = Intent(this,MainActivity::class.java)
        startActivity(intent2)
    }

    private fun getQuestion() {
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        retro.getQuestion().enqueue(object :Callback<List<QuestionModelItem>>{
            override fun onResponse(
                call: Call<List<QuestionModelItem>>,
                response: Response<List<QuestionModelItem>>
            ) {
               for (q in response.body()!!){  //Testing API Outputs
                    Log.e("wow",q.incorrectAnswers.toString())
               }
                var originalList = response.body()!!
                listA = originalList
                nextQuestion(0)
            }
            override fun onFailure(call: Call<List<QuestionModelItem>>, t: Throwable) {
                Log.e("Fail","Failed to get data")
            }
        })
    }

    fun nextQuestion(c : Int){
        textView.setText(listA!![count].question.toString()) //Questions
        val answerCorrect = listA[count].correctAnswer.toString() //Getting answer from API
        val answerWrong:List<String> = listA[count].incorrectAnswers
        val answerCollec = answerWrong + answerCorrect  //Getting answer collections and shuffling
        val answerShuff = answerCollec.toMutableList()
        answerShuff.shuffle()

        for (i in 0 until radioGroup.childCount){ //output of answers to radioButton text
            radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.text = answerShuff[i]
        }
        answerTrue = answerCorrect
        count++
        if(count==5)
            buttonNext.text = "Submit"
    }

    private fun checkAnswer(a:String, b:String):Int{
        return if (a.equals(b,true))
            1
        else
            0
    }

    fun checkButton(view: View){ //Checking button click
        radioId = radioGroup.checkedRadioButtonId
        radioButton = findViewById(radioId)
        answerChoice = radioButton.text.toString()
//        Toast.makeText(this,"Selected " + radioButton.text, Toast.LENGTH_SHORT).show()
    }

}


