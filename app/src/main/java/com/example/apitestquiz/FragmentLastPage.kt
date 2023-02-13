package com.example.apitestquiz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.apitestquiz.model.QuestionModelItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLastPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLastPage(list: List<QuestionModelItem>, position: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listA = list
    private var pos = position

    lateinit var text: TextView
    lateinit var radioGroup: RadioGroup
    lateinit var buttonRight: Button
    lateinit var buttonLeft: Button
    lateinit var radioButton: RadioButton
    lateinit var answerCorrect:String

    private var score:IntArray = IntArray(5)
    private var count:IntArray = IntArray(5)

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

        val view = inflater.inflate(R.layout.fragment_last_page, container, false)
        text = view.findViewById(R.id.textQuestion2)
        radioGroup = view.findViewById(R.id.radioGroup2)
        buttonRight = view.findViewById(R.id.buttonRight)
        buttonLeft = view.findViewById(R.id.buttonLeft)

        buttonLeft.text = "Previous"
        buttonRight.text = "Finish"

        text.text = listA[pos].question
        radioSettings(view,getAnswerCollection(listA[pos]))
        buttonSettings(view,pos)

        return view

    }

    private fun buttonSettings(holder: View, x: Int,){

        buttonLeft.setOnClickListener {
            //TODO - Figuring out to move to previous fragment
        }
        buttonRight.setOnClickListener {
            //TODO -  Figuring out to move to end page and calculate
        }

    }

    private fun radioSettings(holder: View, answerCollection: MutableList<String>,){
        radioGroup.children.forEachIndexed { index, view ->
            radioButton = view as RadioButton
            radioButton.text = answerCollection[index]
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = radioGroup.findViewById(checkedId)
            if(radio.text.toString().equals(answerCorrect,true)){
                if (score[pos]<=0) {
                    count[pos] = 1
                }
                score[pos] = 1
            }else{
                if (score[pos]>=0){
                    count[pos] = 1
                    score[pos] = 0
                }
            }
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