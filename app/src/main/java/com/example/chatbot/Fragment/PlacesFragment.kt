package com.example.chatbot.Fragment

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.chatbot.BuildConfig
import com.example.chatbot.R
import com.example.chatbot.placesSearch.PlacesSearch
import com.example.chatbot.databinding.FragmentSecondBinding
import com.example.chatbot.Network.Apiclient
import com.example.chatbot.Method
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.chatbot.*
import com.example.chatbot.Method.hideKeyboard
import com.example.chatbot.databinding.ShopItemBinding
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.shop_item.*


private var _binding : FragmentSecondBinding? = null
private val binding get() = _binding!!
private lateinit var mMap: GoogleMap
private lateinit var mapFragment: SupportMapFragment
private lateinit var Shopbinding: ShopItemBinding
private lateinit var placeid :String
class PlacesFragment : Fragment() {
    companion object {
        private const val TAG = "PlacesFragment"
        private const val DEFAULT_ZOOM = 18F
        private const val DEFAULT_LATITUDE = 25.043871531367014
        private const val DEFAULT_LONGITUDE = 121.53453374432904
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        doInitialize()//初始化
        setListener()
    }
    private fun doInitialize() {
        // 初始化地圖
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapCallback)
    }
    @SuppressLint("MissingPermission")
    private val mapCallback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        initGoogleMap()
        mMap.apply {
            uiSettings.setAllGesturesEnabled(true)//允許手勢操作
            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            uiSettings.isMapToolbarEnabled = true
        }
    }
    @SuppressLint("PotentialBehaviorOverride")
    private fun initGoogleMap() {
        if (!::mMap.isInitialized) return
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    DEFAULT_LATITUDE,
                    DEFAULT_LONGITUDE
                ), DEFAULT_ZOOM
            )
        )
        mMap.setOnMarkerClickListener { marker ->
            val mask = marker.snippet.toString().split(",")
            val placeId = mask[0]
            val name = mask[1]
            val photoReference = mask[2]
            binding.apply {

                binding.tvPreview.visibility = View.VISIBLE
//                binding.imgPreview.visibility = View.VISIBLE
                binding.frameLayout.visibility = View.VISIBLE

                tvPreview.text = name
                // 讀取Google Place圖片方法
                if (photoReference.isNotEmpty())
                    imgPreview.load(
                        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" +
                                "$photoReference&key=${BuildConfig.GOOGLE_API_KEY}"
                    )
            }
            true
        }
    }
    private fun setListener() {
        binding.btn.setOnClickListener {
            mMap.clear()
            var keyword = binding.editText.text.toString()
            if (keyword != "")
            {
                findNearSearch(keyword)
                binding.editText.hideKeyboard()
            }
            else
            {
                Toast.makeText(requireContext(), "請輸入要查詢的資訊", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun findNearSearch(keyword:String) {
        Apiclient.googlePlaces.getPlaceSearchWithKeyword(
            location = "${DEFAULT_LATITUDE},${DEFAULT_LONGITUDE}",
            radius = 500,
            keyword = keyword,
            language = "zh-TW",
            key = BuildConfig.GOOGLE_API_KEY
        ).enqueue(object : Callback<PlacesSearch> {
            override fun onResponse(
                call: Call<PlacesSearch>,
                response: Response<PlacesSearch>
            ) {
                response.body()?.let { res ->
                    res.results.forEach { result ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val markerOption = MarkerOptions().apply {
                                position(LatLng(result.geometry.location.lat, result.geometry.location.lng))//取得經緯度
                                title(result.name)//取得店家名稱
                                snippet("${result.place_id},${result.name},${result.photos[0].photo_reference}")
                            }
                            mMap.addMarker(markerOption)
                        }
                    }
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
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val strings = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            // 請求權限
            ActivityCompat.requestPermissions(requireActivity(), strings, 1)
        }
    }
}







