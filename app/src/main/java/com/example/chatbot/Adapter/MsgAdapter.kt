package com.example.chatbot.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.OpenAI.Msg
import com.example.chatbot.OpenAI.Messages
import com.example.chatbot.databinding.MsgLeftLayoutBinding
import com.example.chatbot.databinding.MsgRightLayoutBinding

class MsgAdapter(val msgList: List<Msg>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val SENT = 0
        private const val RECEIVED = 1
    }

    private var messages: MutableList<com.example.chatbot.OpenAI.Messages> = ArrayList()

    /**
     * 設定資料
     */
    fun setterData(messages: MutableList<com.example.chatbot.OpenAI.Messages>) {
        this.messages = messages
        notifyDataSetChanged()//通知ListView數據改變了，更新ListView顯示。
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].role == "user")
            SENT
        else
            RECEIVED
    }//role是user回1，不是回0。

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == SENT)
            SentViewHolder(MsgRightLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
            ReceivedViewHolder(MsgLeftLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SentViewHolder)
            holder.bind()
        else if (holder is ReceivedViewHolder)
            holder.bind()
    }

    override fun getItemCount(): Int = messages.size

    inner class SentViewHolder(private val binding: MsgRightLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() { binding.huText.text = messages[adapterPosition].content }
    }

    inner class ReceivedViewHolder(private val binding: MsgLeftLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() { binding.botText.text = messages[adapterPosition].content }
    }
}