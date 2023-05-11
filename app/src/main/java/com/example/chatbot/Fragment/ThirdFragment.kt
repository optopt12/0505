package com.example.chatbot.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.Adapter.NestedData
import com.example.chatbot.Adapter.RestaurantListAdapter
import com.example.chatbot.BuildConfig
import com.example.chatbot.Method
import com.example.chatbot.Method.hideKeyboard
import com.example.chatbot.Network.Apiclient
import com.example.chatbot.R
import com.example.chatbot.databinding.FragmentFirstBinding
import com.example.chatbot.databinding.MapShopBinding
import com.example.chatbot.databinding.ShopItemBinding
import com.example.chatbot.placesDetails.PlacesDetails
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesSearch.PlacesSearch
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ThirdFragment : Fragment(), RestaurantListAdapter.OnCommentButtonClickListener {
    //binding
    private var _binding: MapShopBinding? = null
    private val binding get() = _binding!!
    private var _binding2: FragmentFirstBinding? = null
    private var _commit: ShopItemBinding? = null

    //adapter
    private lateinit var RAdapter: RestaurantListAdapter

    //Rv
    private var msglist: MutableList<data> = ArrayList()//建立可改變的list

    //Google places search
    private var photorefArray: MutableList<String> = ArrayList()
    private var placeidArray: MutableList<String> = ArrayList()
    private var nestedDataList: MutableList<NestedData> = ArrayList()
    private lateinit var placeid: String
    private lateinit var image: String
    private lateinit var photoref: String

    //Google Detail search
    companion object {
        private const val TAG = "ThirdFragment"
//        private const val DEFAULT_ZOOM = 18F
        private const val DEFAULT_LATITUDE = 25.043871531367014
        private const val DEFAULT_LONGITUDE = 121.53453374432904
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapShopBinding.inflate(inflater, container, false)
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
        SearchShop()
    }

    //...
    override fun onCommentButtonClick(data: data) {
        val targetFragment = OpenAIFragment.newInstance(dataName = data.name, datatext = data.text)
        requireActivity().view_pager.setCurrentItem(0)
        requireActivity().tabLayout.getTabAt(0)?.select()
    }

    private fun initRv() {
        binding.rv.apply {
            RAdapter = RestaurantListAdapter(msglist)//建立适配器实例
            RAdapter.onCommentButtonClickListener = this@ThirdFragment
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = RAdapter

            RAdapter.onClick = { data ->
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val b = Bundle()
                b.putParcelable("ThirdtoRdetail", data)
                val fragment = RestaurantDetailFragment()
                fragment.arguments = b
                fragmentTransaction.add(R.id.container, fragment, fragment.javaClass.name)
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
                fragmentTransaction.commit()
            }
        }
    }

    private fun SearchShop() {
            binding.button.setOnClickListener()
            {
                var keyword = binding.editText.text.toString()
                if (keyword != "") {
                    if (binding.rv.childCount > 0) {//清除rv裡面的所有內容
                    msglist.clear()
                    nestedDataList.clear()
                    photorefArray.clear()
                    placeidArray.clear()
                    binding.rv.removeAllViews()
                }
                    binding.editText.hideKeyboard()
                Apiclient.googlePlaces.getPlaceSearchWithKeyword(
                    location = "$DEFAULT_LATITUDE,$DEFAULT_LONGITUDE",
                    radius = 500,
                    language = "zh-TW",
                    keyword = keyword,
                    key = BuildConfig.GOOGLE_API_KEY
                ).enqueue(object : Callback<PlacesSearch> {
                    override fun onResponse(
                        call: Call<PlacesSearch>,
                        response: Response<PlacesSearch>
                    ) {
                        response.body()?.let { res ->
                            res.results.forEach { result ->
                                placeid = result.place_id
                                placeidArray.add(placeid)
                                result.photos.forEach { photo ->
                                    photorefArray.add(photo.photo_reference)
                                }
                            }
                        }
                        for (i in 0..photorefArray.size - 1) {
                            photoref = photorefArray[i]
                            image = "https://maps.googleapis.com/maps/api/place/photo" +
                                    "?maxwidth=4000" +
                                    "&maxheight=4000" +
                                    "&photo_reference=" + photoref +
                                    "&key=" + BuildConfig.GOOGLE_API_KEY
                            nestedDataList.add(NestedData(image))
                        }
                        for (i in 0..placeidArray.size - 1) {
                            placeid = placeidArray[i]
                            image = nestedDataList[i].imageUrl
                            DetailSearch(placeid, image)
                        }
                    }

                    override fun onFailure(
                        call: Call<PlacesSearch>,
                        t: Throwable
                    ) {
                        t.printStackTrace()
                        Method.logE(TAG, "onFailure: ${t.message}")
                    }
                })
                }
                else
                {
                    Toast.makeText(requireContext(), "請輸入要查詢的資訊", Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun DetailSearch(placeid: String, image: String) {
        Apiclient.googlePlaces.getPlaceDetails(
            placeID = placeid,
            language = "zh-TW",
            key = BuildConfig.GOOGLE_API_KEY
        ).enqueue(object : Callback<PlacesDetails> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<PlacesDetails>,
                response: Response<PlacesDetails>
            ) {
                var photoList: MutableList<String> = ArrayList()
                var DetailphotorefArray: MutableList<String> = ArrayList()
                var Detailphotoref: String = " "
                var Detailimage: String = " "
                var address :String = ""
                var phonenumber :String = ""
                var name :String = ""
                //Google places search 評論

                 var author_name: MutableList<String> = ArrayList()
                 var user_language: MutableList<String> = ArrayList()
                 var profile_photo_url: MutableList<String> = ArrayList()
                 var text: MutableList<String> = ArrayList()

                    DetailphotorefArray.clear()
                    photoList.clear()

                response.body()?.let { res ->
                    address = res.result.formatted_address ?: ""
                    name = res.result.name ?: ""
                    phonenumber = res.result.formatted_phone_number ?: ""
                    res.result.photos.forEach { photo ->
                        DetailphotorefArray.add(photo.photo_reference)
                    }
                    res.result.reviews.forEach { Review ->
                        author_name.add(Review.author_name)
                        user_language.add(Review.language)
                        text.add(Review.text)
                        profile_photo_url.add(Review.profile_photo_url)
                    }
                }

                for (i in 0..DetailphotorefArray.size - 1) {
                    Detailphotoref = DetailphotorefArray[i]
                    Detailimage = "https://maps.googleapis.com/maps/api/place/photo" +
                            "?maxwidth=4000" +
                            "&maxheight=4000" +
                            "&photo_reference=" + Detailphotoref +
                            "&key=" + BuildConfig.GOOGLE_API_KEY
                    photoList.add(Detailimage)
                }
                rv(image, photoList, author_name, user_language, profile_photo_url,text,address,phonenumber,name)
            }

            override fun onFailure(
                call: Call<PlacesDetails>,
                t: Throwable
            ) {
                t.printStackTrace()
                Method.logE(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun rv(
        image: String,
        photoList: MutableList<String> = ArrayList(),
        author_name: MutableList<String> = ArrayList(),
        user_language: MutableList<String> = ArrayList(),
        profile_photo_url: MutableList<String> = ArrayList(),
        text: MutableList<String> = ArrayList(),
        address : String,
        phonenumber : String,
        name : String
    ) {
        msglist.add(
            data(
                photoList = photoList,
                formatted_address = address,
                name = name,
                formatted_phone_number = phonenumber,
                image = image,
                author_name = author_name,
                language = user_language,
                text = text,
                profile_photo_url = profile_photo_url
            )
        )
        Log.e("msglist", "${msglist}")
        RAdapter.notifyDataSetChanged()
    }
}

