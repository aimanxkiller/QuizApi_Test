package com.example.apitestquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EndActivity : AppCompatActivity() {

    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        var myScore = intent.getStringExtra("scoreFin")
        textView = findViewById(R.id.textView2)

        textView.text = "Your final score is \n $myScore/5"

    }
}