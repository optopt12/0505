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

    private var data: String? = null
    private var receivedData: List<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("RDetailtoImage")
            receivedData = listOf(data!!)!! // create a new list with data as its only element
            Log.d("receivedData", receivedData.toString())
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


        val test = requireActivity().findViewById<ViewPager2>(R.id.shop_view_pager)

        val adapter = ImageDetailAdapter(receivedData!!)
        binding.apply {
            shopViewPager.adapter = adapter

            shopViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 滑动方向
            shopViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // 页面变化回调
                }
            })
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