package com.example.apitestquiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.apitestquiz.model.QuestionModelItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
interface FragmentCommunicator{
    fun onDataPass(score: Int,count:Int,position:Int)
}

class FragmentFirstPage(list: List<QuestionModelItem>, position: Int) : Fragment() {
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

    lateinit var text:TextView
    lateinit var radioGroup: RadioGroup
    lateinit var radioButton:RadioButton
    lateinit var answerCorrect:String

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
        val view = inflater.inflate(R.layout.fragment_first_page, container, false)
        text = view.findViewById(R.id.textQuestion2)
        radioGroup = view.findViewById(R.id.radioGroup2)

        text.text = "${pos+1}. " + listA[pos].question
        radioSettings(getAnswerCollection(listA[pos]))

        return view
    }

    private fun radioSettings(answerCollection: MutableList<String>,){
        radioGroup.children.forEachIndexed { index, view ->
            radioButton = view  as RadioButton
            radioButton.text = answerCollection[index]
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = radioGroup.findViewById(checkedId)
            if(radio.text.toString().equals(answerCorrect,true)){
                if (score<=0) {
                    count = 1
                    score = 1
                }
            }else{
                if (score>=0){
                    count = 1
                    score = 0
                }
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