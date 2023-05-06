package com.example.chatbot.Adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Fragment.ThirdFragment
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopItemNestedBinding
import com.example.chatbot.placesDetails.data
import com.squareup.picasso.Picasso

class NestedImageAdapter(var photoList:MutableList<String>) :  //只需要MsgList的imgUrl
    RecyclerView.Adapter<NestedImageAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ShopItemNestedBinding) :
        RecyclerView.ViewHolder(binding.root)

    var onClick: ((MutableList<String>, Int) -> Unit) = { _, _ -> }
//    var onClick: ((MutableList<String>) -> Unit) = {}




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val View = ShopItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(View)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val size:Int=300
        val photoUrl = photoList[position]
        holder.binding.imgNested.layoutParams.width = size
        holder.binding.imgNested.layoutParams.height = size
        holder.binding.imgNested.setScaleType(ImageView.ScaleType.CENTER_CROP)
        Picasso.get().load(photoUrl).into(holder.binding.imgNested)
        holder.itemView.setOnClickListener {
            onClick?.invoke(photoList,position)
            Log.d("position", "$position")

        }


//        Log.d("position", "$position")
        Log.d("photosize", "${photoList.size}")

//
//        val photoUrls = photoList[position].photoList
//        for (url in photoUrls) {
//            Picasso.get().load(url).into(holder.binding.imgNested)   }


    }


    override fun getItemCount(): Int = photoList.size

}


data class NestedData(
    val imageUrl: String
)
