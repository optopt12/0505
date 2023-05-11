package com.example.chatbot.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.Adapter.NestedImageAdapter
import com.example.chatbot.Adapter.RestaurantDetailAdapter
import com.example.chatbot.R
import com.example.chatbot.databinding.ShopDetailBinding
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesDetails.detaildata


class RestaurantDetailFragment : Fragment() {
    private var _binding: ShopDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var RAdapter: RestaurantDetailAdapter
    private lateinit var NAdapter: NestedImageAdapter

    private var Detailmsglist: MutableList<detaildata> = ArrayList()//建立可改變的list
    private var photolist: MutableList<String> = ArrayList()//建立可改變的list
    private var receivedData: data? = null
    private lateinit var author_name: String
    private lateinit var language: String
    private lateinit var text: String
    private lateinit var profile_photo_url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receivedData = it.getParcelable("ThirdtoRdetail")
            photolist = receivedData!!.photoList
            for(i in 0 .. receivedData!!.author_name.size - 1)
            {
                author_name = receivedData!!.author_name[i]
                language = receivedData!!.language[i]
                text = receivedData!!.text[i]
                profile_photo_url = receivedData!!.profile_photo_url[i]
                Detailmsglist.add(detaildata(author_name,language,text,profile_photo_url))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShopDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv() //RecyclerView初始化
        Toast.makeText(requireContext(), "${receivedData?.name}", Toast.LENGTH_SHORT).show()
        Log.d("Detailmsglist", Detailmsglist.toString() )
    }

    private fun initRv() {
        binding.DetailrvS.apply {
            RAdapter = RestaurantDetailAdapter(Detailmsglist)//建立适配器实例
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )  //布局为线性垂直
            adapter = RAdapter
            RAdapter.notifyDataSetChanged()
        }
        binding.DetailrvH.apply {
            NAdapter = NestedImageAdapter(photolist)//建立适配器实例
            Log.d("photolist", photolist.toString())
//            NAdapter = NestedImageAdapter(Detailmsglist)//建立适配器实例
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )  //布局为线性垂直
            adapter = NAdapter
            NAdapter.notifyDataSetChanged()
            NAdapter.onClick = { data,position ->
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val b = Bundle()
//                b.putString("RDetailtoImage", data)
                b.putStringArrayList("RDetailtoImage", ArrayList(data))
                b.putInt("position",position)



                val fragment = ImageDetailFragment()
                fragment.arguments = b
                fragmentTransaction.add(R.id.container, fragment, fragment.javaClass.name)
//                fragmentTransaction.replace(R.id.container, fragment, fragment.javaClass.name)
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
                fragmentTransaction.commit()

//                val currentFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.container)
//                if (currentFragment != null) {
//                    fragmentTransaction.hide(currentFragment)
//                }
            }


        }
    }



//    private fun rv(image: String) {
//        msglist.add(
//            data(
//                formatted_address = address,
//                name = name,
//                formatted_phone_number = phonenumber,
//                image = image
//            )
//        )
////        Log.d("msg", "msglist: $msglist\n")
////        Log.d("nestedDataList", "nestedDataList: $nestedDataList\n")
////        Log.d("photorefArray", "photorefArray: $photorefArray\n")
////        Log.d("image", "image: $image\n")
//        RAdapter.notifyDataSetChanged()
//
//
//    }

}