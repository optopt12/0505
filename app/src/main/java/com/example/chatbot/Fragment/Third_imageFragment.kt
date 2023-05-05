package com.example.chatbot.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chatbot.databinding.FragmentThirdImageBinding
import com.example.chatbot.databinding.MapShopBinding

private var _binding: FragmentThirdImageBinding? = null
private val binding get() = _binding!!

private lateinit var photoUrl: String

// TODO: use navigate instead of intent
class Third_imageFragment : Fragment() {

    companion object {
        const val ARG_PHOTO_URL = "arg_photo_url"
        const val ARG_SOME_OTHER_DATA = "arg_some_other_data"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdImageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val photoUrl = it.getString(ARG_PHOTO_URL)
            val someOtherData = it.getString(ARG_SOME_OTHER_DATA)
            // 取得傳遞過來的資料後，就可以使用它們了
        }
        Log.d("photoUrl", photoUrl.toString())
        val data = arguments?.getString("data_key")
        Log.d("data", data.toString())



    }

}