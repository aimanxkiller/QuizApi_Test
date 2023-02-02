package com.example.apitestquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class SelectionActivity : AppCompatActivity() {
    private lateinit var spinner:Spinner
    lateinit var buttonNext:Button
    lateinit var selection:String

    private var category = arrayOf("science","arts_and_literature","film_and_tv","food_and_drink","general_knowledge","geography","history","sport_and_leisure","society_and_culture","music")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        buttonNext = findViewById(R.id.buttonSel)
        spinner = findViewById(R.id.dropdownList)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,category)
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