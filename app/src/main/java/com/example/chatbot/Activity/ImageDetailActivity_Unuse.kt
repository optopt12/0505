package com.example.chatbot.Activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.chatbot.Adapter.ImageDetailAdapter
import com.example.chatbot.R
import com.example.chatbot.placesDetails.data

class ImageDetailActivity_Unuse : AppCompatActivity() {


    private var msglist: MutableList<data> = ArrayList()//建立可改變的list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        testrv()

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

//https://www.edamam.com/food-img/296/296ff2b02ef3822928c3c923e22c7d19.jpg

//        testuniformimage("https://www.edamam.com/food-img/296/296ff2b02ef3822928c3c923e22c7d19.jpg")

        val images=listOf(
            "https://www.edamam.com/food-img/296/296ff2b02ef3822928c3c923e22c7d19.jpg",
            "https://www.edamam.com/food-img/515/515af390107678fce1533a31ee4cc35b.jpeg",
            "https://www.edamam.com/food-img/42c/42c006401027d35add93113548eeaae6.jpg"
        )

//        Log.d("test",test)




        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val adapter = ImageDetailAdapter(images)
        viewPager.adapter = adapter

        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 滑动方向
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 页面变化回调
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "按下左上角返回鍵", Toast.LENGTH_SHORT).show();
            finish()
        }
        return super.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item)
    }






}