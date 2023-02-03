package com.example.apitestquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SelectionActivity : AppCompatActivity() {
    private var category:MutableList<String> = mutableListOf()
    private lateinit var spinner:Spinner
    private lateinit var buttonNext:Button
    lateinit var selection:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        buttonNext = findViewById(R.id.buttonSel)
        spinner = findViewById(R.id.dropdownList)

        getQuizCat()

        buttonNext.setOnClickListener {
            buttonClick()
        }
    }

    private fun spinnerEnabler(x: QuizCat){

        category.add(x.SportLeisure.toString())
        category.add(x.SocietyCulture.toString())
        category.add(x.Science.toString())
        category.add(x.Music.toString())
        category.add(x.History.toString())
        category.add(x.Geography.toString())
        category.add(x.GeneralKnowledge.toString())
        category.add(x.ArtsLiterature.toString())
        category.add(x.FoodDrink.toString())
        category.add(x.FilmTV.toString())

        Log.e("Test",category[0])

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,category)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val y = category[position].split(",")
                selection = y[0].replace("[","").replace("]","")
                Toast.makeText(this@SelectionActivity, selection, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun getQuizCat(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)
        retro.getCategories().enqueue(object : Callback<QuizCat>{
            override fun onResponse(call: Call<QuizCat>, response: Response<QuizCat>) {
                spinnerEnabler(response.body()!!)
            }

            override fun onFailure(call: Call<QuizCat>, t: Throwable) {
                Log.e("Fail","Failed to get data: "+ t.message)
            }
        })
    }

    private fun buttonClick(){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("randomType",selection)
        startActivity(intent)
    }

}