package com.example.chatbot.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopItemNestedBinding
import com.example.chatbot.databinding.ShopItemScrollBinding
import com.example.chatbot.databinding.ShopItemScrollItemBinding

class ImageDetailAdapter(private val imagesList: List<String>) : RecyclerView.Adapter<ImageDetailAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: ShopItemScrollItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val view = ShopItemScrollItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(view)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(imagesList[position])
            .into(holder.binding.img)
//        Picasso.get().load(images[position]).into(holder.imageView)


    }

    override fun getItemCount(): Int = imagesList.size

//    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.img)
//    }
}