package com.example.apitestquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.View
import android.widget.*
import okhttp3.internal.Internal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var textView: TextView
    lateinit var buttonNext: Button
    var x = 0;

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

                var x = 0

                buttonNext.setOnClickListener {
                    outerLoop@ while (x < 6){

//                    for (q in response.body()!!){  //Testing API Outputs
//                        Log.e("wow",q.incorrectAnswers.toString())
//                    }
//                      Log.e("wow", response.body()!![2].incorrectAnswers.toString())
                        if (x<5) {
                            textView.setText(response.body()!![x].question.toString())  //Question 1

                            val answerCorrect =
                                response.body()!![x].correctAnswer.toString() //Getting answer from API
                            val answerWrong = response.body()!![x].incorrectAnswers.toString()
                                .replace("[", "")
                                .replace("]", "")
                                .split(", ")

                            val answerCollec =
                                answerWrong + answerCorrect  //Getting answer collections and shuffling
                            val answerShuff = answerCollec.toMutableList()
                            Collections.shuffle(answerShuff)

                            for (i in 0 until radioGroup.childCount) { //output of answers to radioButton text
                                radioButton = radioGroup.getChildAt(i) as RadioButton
                                radioButton.text = answerShuff[i]
                            } //end
                        }

                        x++

                        if (x<6){
                            break@outerLoop
                        }else{
                            var intent = Intent(this@MainActivity,EndActivity::class.java)
                            startActivity(intent)
                        }

                    }
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


