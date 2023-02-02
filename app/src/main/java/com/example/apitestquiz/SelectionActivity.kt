package com.example.apitestquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class SelectionActivity : AppCompatActivity() {
    private lateinit var spinner:Spinner
    private lateinit var buttonNext:Button
    lateinit var selection:String

    private var categoryName = arrayOf("Science","Arts and Literature","Film and Tv","Food and Drink","General Knowledge","Geography","History","Sports and Leisure","Society and Culture","Music")
    private var category = arrayOf("science","arts_and_literature","film_and_tv","food_and_drink","general_knowledge","geography","history","sport_and_leisure","society_and_culture","music")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

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
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        buttonNext.setOnClickListener {
            buttonClick()
        }
    }

    private fun buttonClick(){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("randomType",selection)
        startActivity(intent)
    }

}