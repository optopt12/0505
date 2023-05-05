package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Activity.MainActivity
import com.example.chatbot.Fragment.RestaurantDetailFragment
import com.example.chatbot.Fragment.ThirdFragment
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopDetailBinding
import com.example.chatbot.databinding.ShopDetailItemBinding
import com.example.chatbot.databinding.ShopItemBinding
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class RestaurantDetailAdapter(var DetailMsgList: MutableList<data>) :
    RecyclerView.Adapter<RestaurantDetailAdapter.ItemViewHolder>() {
    /**
     * 設定資料
     */
    inner class ItemViewHolder(val binding: ShopDetailItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = DetailMsgList[position]
        holder.binding.authorName.text = data.author_name
        holder.binding.text.text = data.text
        holder.binding.language.text = data.language
        Picasso.get().load(data.profile_photo_url).into(holder.binding.imageView)
    }
    override fun getItemCount(): Int = DetailMsgList.size
}