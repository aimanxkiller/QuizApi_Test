package com.example.apitestquiz.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.apitestquiz.R
import com.example.apitestquiz.model.QuestionModelItem

class ViewPagerAdapter(private var list:List<QuestionModelItem>):RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {
    private lateinit var radioButton: RadioButton

    private var score:IntArray = IntArray(list.size)
    private var count:IntArray = IntArray(list.size)
    private var answerCorrect = Array(list.size){""}


    inner class Pager2ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        //item_page content
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup2)
        val textView: TextView = itemView.findViewById(R.id.textQuestion2)
        val buttonRight: Button = itemView.findViewById(R.id.buttonRight)
        val buttonLeft: Button = itemView.findViewById(R.id.buttonLeft)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position:Int){
        holder.textView.text = ("${position+1}. "+ list[position].question)
        radioSettings(holder,position,getAnswerCollection(list,position))
        buttonSettings(holder,position)
    }

    private fun radioSettings(holder: Pager2ViewHolder,position: Int,answers: MutableList<String>) {
        holder.radioGroup.children.forEachIndexed { index, view ->
            radioButton = view as RadioButton
            radioButton.text = answers[index]
        }


        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio:RadioButton = holder.radioGroup.findViewById(checkedId)
            if(radio.text.toString().equals(answerCorrect[position],true)){
                if (score[position]<=0) {
                    count[position] = 1
                    score[position] = 1
                }
            }else{
                if (score[position]>=0){
                    count[position] = 1
                    score[position] = 0
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun buttonSettings(holder: Pager2ViewHolder, position:Int) {
        holder.buttonRight.setOnClickListener {
            (holder.itemView.parent as RecyclerView).smoothScrollToPosition(position + 1)
        }
        holder.buttonLeft.setOnClickListener {
            (holder.itemView.parent as RecyclerView).smoothScrollToPosition(position - 1)
        }
        holder.buttonRight.text = "Next"
        holder.buttonLeft.text = "Previous"

        when(position){
            0-> {
                holder.buttonLeft.visibility = View.INVISIBLE
                holder.buttonRight.visibility = View.VISIBLE
            }
            4 ->{
                holder.buttonLeft.visibility = View.VISIBLE
                holder.buttonRight.visibility = View.VISIBLE
                holder.buttonRight.text = "Finish"

                holder.buttonRight.setOnClickListener {
                    val context = holder.itemView.context
                    if(count.sum() == 5){
                        val intent = Intent (context, EndActivity::class.java)
                        intent.putExtra("scoreFin",score.sum().toString())
                        context.startActivity(intent)
                    }else{ Toast.makeText(context,"Answer All Question",Toast.LENGTH_SHORT).show() }
                }
            }
            else -> {
                holder.buttonLeft.visibility = View.VISIBLE
                holder.buttonRight.visibility = View.VISIBLE
            }
        }
    }

    private fun getAnswerCollection(x:List<QuestionModelItem>,y:Int): MutableList<String> {
        answerCorrect[y] = x[y].correctAnswer //Getting answer from API
        val answerWrong:List<String> = x[y].incorrectAnswers
        val answerCollect = answerWrong + x[y].correctAnswer  //Getting answer collections and shuffling
        val answerShuffle = answerCollect.toMutableList()
        answerShuffle.shuffle()

        return answerShuffle
    }

}

