package com.example.apitestquiz

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var textView: TextView
    lateinit var buttonNext: Button
     var answerTrue:String = "a"
     var answerChoice:String = "b"

    lateinit var listA : List<QuestionModelItem>

    var count = 0
    var score = 0 //using for scores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.textQuestion)
        buttonNext = findViewById(R.id.buttonNext)

        getQuestion()

        buttonNext.setOnClickListener {
            score += checkAnswer(answerChoice,answerTrue)
            Log.e("wow", "$score is total")
//            Toast.makeText(this,"Score is $score /5",Toast.LENGTH_SHORT).show()
            if (count<5){
                nextQuestion(count)
            }else {
                var intent = Intent(this@MainActivity,EndActivity::class.java)
                Log.e("Ending", "The $score is total")
                intent.putExtra("scoreFin",score.toString())
                startActivity(intent)
            }

        }


//        for (x in 1..4){
//
//            getQuestion(x)
//
//            if(x<5){
//                score += checkAnswer(answerChoice,answerTrue)
//            }
//        }
//            score += checkAnswer(answerChoice,answerTrue)
//            Toast.makeText(this,"Score is $score",Toast.LENGTH_SHORT).show()
//
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
//               Log.e("wow", response.body()!![2].incorrectAnswers.toString()) //TestApi End

                var originalList = response.body()!!

                setQuestion(originalList)

//                textView.setText(response.body()!![count].question.toString())  //Questions
//                val answerCorrect = response.body()!![count].correctAnswer.toString() //Getting answer from API
//                val answerWrong = response.body()!![count].incorrectAnswers.toString()
//                    .replace("[","")
//                    .replace("]","")
//                    .split(", ")
//
//                val answerCollec = answerWrong + answerCorrect  //Getting answer collections and shuffling
//                val answerShuff = answerCollec.toMutableList()
//                answerShuff.shuffle()
//
//                for (i in 0 until radioGroup.childCount){ //output of answers to radioButton text
//                    radioButton = radioGroup.getChildAt(i) as RadioButton
//                    radioButton.text = answerShuff[i]
//                }
//
//                answerTrue = answerCorrect

                //Need to pause here till user click button

            }

            override fun onFailure(call: Call<List<QuestionModelItem>>, t: Throwable) {
                Log.e("Fail","Failed to get data")
            }

        })
    }

    fun setQuestion(listQ : List<QuestionModelItem>){

        listA = listQ
        textView.setText(listQ!![count].question.toString()) //Questions
        val answerCorrect = listQ[count].correctAnswer.toString() //Getting answer from API
        val answerWrong = listQ[count].incorrectAnswers.toString()
            .replace("[","")
            .replace("]","")
            .split(", ")

        val answerCollec = answerWrong + answerCorrect  //Getting answer collections and shuffling
        val answerShuff = answerCollec.toMutableList()
        answerShuff.shuffle()

        for (i in 0 until radioGroup.childCount){ //output of answers to radioButton text
            radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.text = answerShuff[i]
        }
//
        answerTrue = answerCorrect
        count++

    }

    fun nextQuestion(c : Int){

        textView.setText(listA!![count].question.toString()) //Questions
        val answerCorrect = listA[count].correctAnswer.toString() //Getting answer from API
        val answerWrong:List<String> = listA[count].incorrectAnswers
//            .toString()
//            .replace("[","")
//            .replace("]","")
//            .split(", ")

        val answerCollec = answerWrong + answerCorrect  //Getting answer collections and shuffling
        val answerShuff = answerCollec.toMutableList()
        answerShuff.shuffle()

        for (i in 0 until radioGroup.childCount){ //output of answers to radioButton text
            radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.text = answerShuff[i]
        }
//
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
        var radioId:Int = radioGroup.checkedRadioButtonId
        radioButton = findViewById(radioId)

        answerChoice = radioButton.text.toString()
//        Toast.makeText(this,"Selected " + radioButton.text, Toast.LENGTH_SHORT).show()
    }

}


