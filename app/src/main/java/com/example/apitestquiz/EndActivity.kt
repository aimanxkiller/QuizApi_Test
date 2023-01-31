package com.example.apitestquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class EndActivity : AppCompatActivity() {

    private lateinit var textView:TextView
    private lateinit var textView2:TextView
    private lateinit var buttonReset:Button
    private var choices = arrayOf("science","arts_and_literature","film_and_tv","food_and_drink","general_knowledge","geography","history","sport_and_leisure","society_and_culture","music")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        val myScore = intent.getStringExtra("scoreFin")?.toInt()
        textView = findViewById(R.id.textView2)
        val score = myScore?.times(5)
        textView.text = ("Your final score is \n $score/25")
        buttonReset = findViewById(R.id.buttonReset)
        val x = choices.random()
        textView2 = findViewById(R.id.textView3)


        buttonReset.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("randomType",x)
            startActivity(intent)
        }
    }
}