package com.example.apitestquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EndActivity : AppCompatActivity() {

    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        var myScore = intent.getStringExtra("scoreFin")?.toInt()
        textView = findViewById(R.id.textView2)
        var score = myScore?.times(5)
        textView.text = "Your final score is \n $score/25"

    }
}