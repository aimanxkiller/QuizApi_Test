package com.example.apitestquiz.viewmodel

import android.provider.MediaStore.Audio.Radio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.apitestquiz.R
import com.example.apitestquiz.model.QuestionModelItem

class ViewPagerAdapter(private var list:List<QuestionModelItem>):RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    lateinit var radioButton: RadioButton

    inner class Pager2ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup2)
        val textView: TextView = itemView.findViewById(R.id.textQuestion2)
        val buttonNext: Button = itemView.findViewById(R.id.buttonNext2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position:Int){
        holder.textView.text = list[position].question

        var answers = getAnswerCollection(list,position)

        holder.radioGroup.children.forEachIndexed { index, view ->
            radioButton = view as RadioButton
            radioButton.text = answers[position]
        }

    }

    private fun getAnswerCollection(x:List<QuestionModelItem>,y:Int): MutableList<String> {
        val answerCorrect = x[y].correctAnswer //Getting answer from API
        val answerWrong:List<String> = x[y].incorrectAnswers
        val answerCollect = answerWrong + answerCorrect  //Getting answer collections and shuffling
        val answerShuffle = answerCollect.toMutableList()
        answerShuffle.shuffle()

        return answerShuffle
    }

}