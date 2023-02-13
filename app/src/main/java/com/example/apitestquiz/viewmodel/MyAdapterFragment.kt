package com.example.apitestquiz.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apitestquiz.FragmentFirstPage
import com.example.apitestquiz.FragmentLastPage
import com.example.apitestquiz.FragmentMidPage
import com.example.apitestquiz.model.QuestionModelItem

class MyAdapterFragment(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle, var list: List<QuestionModelItem>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> FragmentFirstPage(list[position],position)
            (list.size - 1)-> FragmentLastPage()
            else -> FragmentMidPage()
        }
    }

}