package com.example.chatbot.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.chatbot.Adapter.ImageDetailAdapter
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopItemScrollBinding


class ImageDetailFragment : Fragment() {

    private var _binding: ShopItemScrollBinding? = null
    private val binding get() = _binding!!

    private var data: MutableList<String>? = null
    private var receivedData: List<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data=it.getStringArrayList("RDetailtoImage")
            receivedData = data!!.toList()
            Log.d("imagedetail_receivedData", receivedData.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShopItemScrollBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position")  // 獲取當前位置，如果為空則設置為0


        val adapter = ImageDetailAdapter(receivedData!!)
        binding.apply {
            shopViewPager.adapter = adapter
            shopViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 滑动方向
            shopViewPager.setCurrentItem(position!!,false) //將當前位置設置為ViewPager的當前選中項目，並取消動畫
        }

    }


}


//TODO 全部有圖片載入的地方要加error image
//Picasso.get()
//.load(photoUrl)
//.placeholder(R.drawable.your_frame_drawable)
//.error(R.drawable.your_error_drawable)
//.into(holder.binding.imgNested)

//Glide.with(holder.itemView.context)
//.load(photoUrl)
//.placeholder(R.drawable.your_frame_drawable)
//.error(R.drawable.your_error_drawable)
//.into(holder.binding.imgNested)