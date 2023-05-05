package com.example.chatbot.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chatbot.Fragment.*

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0->{
                OpenAIFragment()
            }
            1->{
                PlacesFragment()
            }
            2->{
                ThirdFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}