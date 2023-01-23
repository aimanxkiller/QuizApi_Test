package com.example.apitestquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var textView: TextView
    lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.textQuestion)
        buttonNext = findViewById(R.id.buttonNext)

        getQuestion()

    }

    private fun getQuestion(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        retro.getQuestion().enqueue(object :Callback<List<QuestionModelItem>>{
            override fun onResponse(
                call: Call<List<QuestionModelItem>>,
                response: Response<List<QuestionModelItem>>
            ) {

//               for (q in response.body()!!){  //Testing API Outputs
//                    Log.e("wow",q.incorrectAnswers.toString())
//               }
//               Log.e("wow", response.body()!![2].incorrectAnswers.toString())

                textView.setText(response.body()!![0].question.toString())  //Question 1

                val answerCorrect = response.body()!![0].correctAnswer.toString() //Getting answer from API
                val answerWrong = response.body()!![0].incorrectAnswers.toString()
                    .replace("[","")
                    .replace("]","")
                    .split(", ")

                val answerCollec = answerWrong + answerCorrect  //Getting answer collections and shuffling
                val answerShuff = answerCollec.toMutableList()
                Collections.shuffle(answerShuff)

                for (i in 0 until radioGroup.childCount){ //output of answers to radioButton text
                    radioButton = radioGroup.getChildAt(i) as RadioButton
                    radioButton.text = answerShuff[i]
                }

            }

            override fun onFailure(call: Call<List<QuestionModelItem>>, t: Throwable) {
                Log.e("Fail","Failed to get data")
            }

        })
    }

    fun checkButton(view: View){ //Checking button click
        var radioId:Int = radioGroup.checkedRadioButtonId
        radioButton = findViewById(radioId)
        Toast.makeText(this,"Selected " + radioButton.text, Toast.LENGTH_SHORT).show()
    }

}


