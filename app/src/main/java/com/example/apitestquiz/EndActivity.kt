package com.example.apitestquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class EndActivity : AppCompatActivity() {

    private lateinit var textView:TextView
    private lateinit var buttonReset:Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        val myScore = intent.getStringExtra("scoreFin")?.toInt()
        textView = findViewById(R.id.textView2)
        val score = myScore?.times(5)
        textView.text = ("Your final score is \n $score/25")
        buttonReset = findViewById(R.id.buttonReset)

        buttonReset.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}