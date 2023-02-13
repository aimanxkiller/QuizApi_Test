package com.example.apitestquiz.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.apitestquiz.FragmentFirstPage
import com.example.apitestquiz.FragmentLastPage
import com.example.apitestquiz.FragmentMidPage
import com.example.apitestquiz.model.QuestionModelItem

class MyAdapterFragment(
    activity: AppCompatActivity,
    private val list: List<QuestionModelItem>
):FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FragmentFirstPage(list,position)
            list.size -> FragmentLastPage(list,position)
            else -> FragmentMidPage(list,position)
        }
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }
}