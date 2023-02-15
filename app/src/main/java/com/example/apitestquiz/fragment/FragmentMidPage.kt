package com.example.apitestquiz.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.apitestquiz.R
import com.example.apitestquiz.model.QuestionModelItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */

interface FragmentCommunicator{
    fun onDataPass(score: Int,count:Int,position:Int)
    fun onPagePass(page:Int)
}

class FragmentMidPage(list: List<QuestionModelItem>, position: Int) : Fragment() {
    private var listener: FragmentCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCommunicator){
            listener=context
        }else
            throw java.lang.ClassCastException("$context must implement FragmentCommunicator")
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listA = list
    private var pos = position

    lateinit var text: TextView
    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var buttonL:Button
    lateinit var buttonR:Button

    private lateinit var answerCorrect:String
    private var score:Int =0
    private var count:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_mid_page, container, false)
        text = view.findViewById(R.id.textQuestion2)
        radioGroup = view.findViewById(R.id.radioGroup2)
        buttonL = view.findViewById(R.id.buttonFragLeft)
        buttonR= view.findViewById(R.id.buttonFragRight)

        text.text = "${pos+1}. " +listA[pos].question
        radioSettings(getAnswerCollection(listA[pos]))

        setButtons()

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setButtons() {
        buttonR.text = "Next"
        buttonL.text = "Previous"
        buttonL.setOnClickListener {
            listener?.onPagePass(pos-2)
        }
        buttonR.setOnClickListener {
            listener?.onPagePass(pos)
        }
        when (pos){
            0 ->{
                buttonL.visibility = View.INVISIBLE
            }
            (listA.size - 1) ->{
                buttonR.text = "Finish"
                buttonR.setOnClickListener {
                    listener?.onPagePass(pos)
                }
            }
            else ->{
                buttonL.visibility = View.VISIBLE
            }
        }
    }

    private fun radioSettings(answerCollection: MutableList<String>){
        radioGroup.children.forEachIndexed { index, view ->
            radioButton = view as RadioButton
            radioButton.text = answerCollection[index]
        }

        //updated no nesting required
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = radioGroup.findViewById(checkedId)
            count = 1
            score = if(radio.text.toString().equals(answerCorrect,true)){
                1
            }else{
                0
            }
            listener?.onDataPass(score,count,pos)
        }
    }

    private fun getAnswerCollection(x:QuestionModelItem): MutableList<String> {
        answerCorrect = x.correctAnswer
        val answerWrong:List<String> = x.incorrectAnswers
        val answerCollect = answerWrong + x.correctAnswer  //Getting answer collections and shuffling
        val answerShuffle = answerCollect.toMutableList()
        answerShuffle.shuffle()

        return answerShuffle
    }

}