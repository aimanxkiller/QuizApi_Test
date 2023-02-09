package com.example.apitestquiz.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apitestquiz.FragmentFirstPage
import com.example.apitestquiz.FragmentLastPage
import com.example.apitestquiz.FragmentMidPage

class MyAdapterFragment(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return FragmentFirstPage()
            3-> return FragmentLastPage()
            else -> return FragmentMidPage()
        }
    }

}