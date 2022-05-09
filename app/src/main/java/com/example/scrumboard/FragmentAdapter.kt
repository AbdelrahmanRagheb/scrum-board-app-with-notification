package com.example.scrumboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.scrumboard.fragments.StoriesFinishedFragment
import com.example.scrumboard.fragments.StoriesFragment
import com.example.scrumboard.fragments.StoriesInProgressFragment
import com.example.scrumboard.fragments.StoriesNotStartedFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return StoriesFragment()
            1->return StoriesNotStartedFragment()
            2->return StoriesInProgressFragment()

        }
        return StoriesFinishedFragment()
    }
}