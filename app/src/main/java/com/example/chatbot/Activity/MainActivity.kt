package com.example.chatbot.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.chatbot.Adapter.ViewPagerAdapter
import com.example.chatbot.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.isUserInputEnabled = false
        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        view_pager.adapter = adapter

        TabLayoutMediator(tabLayout,view_pager){tab,position->
            when(position){
                0->{
                    tab.text = "聊天室"
                }
                1->{
                    tab.text = "尋找餐廳"
                }
                2-> {
                    tab.text = "餐廳資訊"
                }
                3-> {
                    tab.text = "營養資訊"
                }
            }
        }.attach()
    }
}