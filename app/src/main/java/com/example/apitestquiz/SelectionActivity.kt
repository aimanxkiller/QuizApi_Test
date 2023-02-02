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
    //Unable to get category name and variable from API
    private var categoryName:MutableList<String> = mutableListOf()
    private var category:MutableList<String> = mutableListOf()
    lateinit var categoryAPI:QuizCat
    private lateinit var spinner:Spinner
    private lateinit var buttonNext:Button
    lateinit var selection:String
    private var temp:List<String> = listOf("a","b")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        getQuizCat()

        buttonNext = findViewById(R.id.buttonSel)
        spinner = findViewById(R.id.dropdownList)

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categoryName)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selection = category[position]
                Toast.makeText(this@SelectionActivity, selection, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        buttonNext.setOnClickListener {
            buttonClick()
        }
    }

    private fun getQuizCat(){
        val retro = Retro().getRetroClient().create(QuestionApi::class.java)

        retro.getCategories().enqueue(object : Callback<QuizCat>{
            override fun onResponse(call: Call<QuizCat>, response: Response<QuizCat>) {

                categoryAPI = response.body()!!

                //Unable to get the variable from here to outside
                val split = categoryAPI.toString().replace("QuizCat(","").split("],")
                val split2 = split

                split.forEachIndexed { index, s ->
                    var x = split2[index].replace("])","").split("=[")
                    categoryName.add(x[0])
                    category.add(x[1])
                }
            }

            override fun onFailure(call: Call<QuizCat>, t: Throwable) {
                Log.e("Fail","Failed to get data")
            }

        })

    }

    private fun buttonClick(){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("randomType",selection)
        startActivity(intent)
    }

}