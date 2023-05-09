package com.example.chatbot.Fragment

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.chatbot.Network.Apiclient
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.widget.Toast
import com.example.chatbot.Adapter.MsgAdapter
import com.example.chatbot.Adapter.NestedData
import com.example.chatbot.Method
import com.example.chatbot.OpenAI.Msg
import com.example.chatbot.databinding.MapShopBinding
import com.example.chatbot.databinding.ShopItemBinding
import com.example.chatbot.placesDetails.data
import com.example.chatbot.placesDetails.detaildata
import kotlinx.android.synthetic.main.shop_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

private const val SPEECH_REQUEST_CODE = 0

class OpenAIFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentFirstBinding? = null
    private var _binding2: ShopItemBinding? = null

    private lateinit var msgAdapter: MsgAdapter

    private var answer: String = "發送訊息以獲得回覆"
    private var msgList: MutableList<Msg> = ArrayList()//建立可改變的list
    private var tts: TextToSpeech? = null
    private var receivedData: data? = null
    private var comment: MutableList<String> = ArrayList()
    private var shopname: String = " "

    private val sendMessages: MutableList<com.example.chatbot.OpenAI.Messages> = mutableListOf()
    private val currentMessages: MutableList<com.example.chatbot.OpenAI.Messages> = mutableListOf()

    private var dataName: String? = null
    private var dataText: ArrayList<String>? = null


    companion object {
        private var staticDataName: String? = null
        private var staticDataText: MutableList<String>? = null
        fun newInstance(dataName: String, datatext: MutableList<String>): OpenAIFragment {
            val fragment = OpenAIFragment()
            val bundle = Bundle()
            bundle.putString("data_name", dataName)
            bundle.putStringArrayList("data_text", ArrayList(datatext))

            staticDataName = dataName
            staticDataText = datatext

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)


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
        displaySpeechRecognizer()//語音辨識
        setListener()//發送訊息與ai對話
        textToSpeech() //文字轉語音


//        comment()

    }


    private fun setListener() {
        binding.sendButton.setOnClickListener {
            val message = editText.text.toString()
            sendMessage(message = message, showmessage = message)
        }
    }

//    private fun comment() {
//        _binding2?.btnComment?.setOnClickListener{
//            val fragment = ThirdFragment()
//            fragment.setCallbackListener(object : ThirdFragment.CallbackListener{
//                override fun onCallback(data: data) {
//                        comment = data.text
//                        shopname = data.name
//                        val message = "以下是" + shopname +
//                                "的評論 請幫我依照以下評論 做出評分 評分從1到10 並且回覆限制在50個字以內" + comment
//                        sendMessage(message)
//                        Toast.makeText(requireContext(), "發送成功", Toast.LENGTH_SHORT).show()
//                        Log.d("message", "message: $message\n")
//                }
//            })
//            arguments?.let {
//                receivedData = it.getParcelable("ThirdtoRdetail")
//
//            }
//        }
//    }

    private fun initRv() {
        binding.recyclerView.apply {
            msgAdapter = MsgAdapter(msgList)   //建立适配器实例
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )  //布局为线性垂直
            adapter = msgAdapter
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }//打完字自動把鍵盤收起來

    private fun displaySpeechRecognizer() {
        voice_button.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
            }
            // This starts the activity and populates the intent with the speech text.
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        }

    }


    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        Log.d("setArguments", dataName.toString())

        arguments?.let {
            dataName = it.getString("data_name")
            dataText = it.getStringArrayList("data_text")
            // 根據需要進行相應的操作
            Log.d("dataName", dataName.toString())
            Log.d("datatext", dataText.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume")
        binding.editText.setText(staticDataName)
        Log.d("staticDataName", staticDataName.toString())
        Log.d("staticDataText", staticDataText.toString())


        if (staticDataText != null) {

            comment = staticDataText as MutableList<String>
            shopname = staticDataName!!
            val message = "以下是" + shopname +
                    "的評論 請幫我依照以下評論 做出評分 評分從1到10 並且回覆限制在50個字以內" + comment

            val show = "正在幫您分析${shopname}的評論，請稍等"
            sendMessage(message = message, showmessage = show)


        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", "onPause")
        staticDataName = null
        staticDataText = null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("data_name", dataName)
        outState.putStringArrayList("data_text", dataText)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                    results[0]
                }.toString()
            binding.editText.setText(spokenText)
            // Do something with spokenText.
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun textToSpeech() {

        tts = TextToSpeech(context, OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val ttsLang = tts!!.setLanguage(Locale.TRADITIONAL_CHINESE)
                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language is not supported!")
                } else {
                    Log.i("TTS", "Language Supported.")
                }
                Log.i("TTS", "Initialization success.")
            } else {
                Toast.makeText(context, "TTS Initialization failed!", Toast.LENGTH_SHORT).show()
            }
        })
        speech_button!!.setOnClickListener {
            val data = answer
            Log.i("TTS", "button clicked: $data")
            tts!!.setPitch(1F)    // 語調(1 為正常；0.5 為低一倍；2 為高一倍)
            tts!!.setSpeechRate(1F)    // 速度(1 為正常；0.5 為慢一倍；2 為快一倍)
            val speechStatus = tts!!.speak(data, TextToSpeech.QUEUE_FLUSH, null)
            if (speechStatus == TextToSpeech.ERROR) {
                Log.e("TTS", "Error in converting Text to Speech!")
            }
        }
        tts!!.shutdown()   //釋放資源?
    }

    private fun sendMessage(message: String, showmessage: String) {
        binding.run {
            if (message.isNotEmpty()) {

                progressBar.progress = 0
                ll_progress.visibility = View.VISIBLE //讀取條跳出

                editText.setText("")
                // 定義要傳送的資料
                val reqMessage =
                    com.example.chatbot.OpenAI.Messages(role = "user", content = message)

                val ShowMessage =
                    com.example.chatbot.OpenAI.Messages(role = "user", content = showmessage)

                // 加入到傳送用的資料列表
                sendMessages.add(reqMessage)
                // 加入到顯示用的資料列表
                currentMessages.add(ShowMessage)
                // 先刷新列表
                msgAdapter.setterData(currentMessages)
                recyclerView.scrollToPosition(currentMessages.size - 1)
                // 呼叫API
                Apiclient.openAI.sendChatGPT(com.example.chatbot.OpenAI.ChatGPTReq(messages = sendMessages))
                    .enqueue(object :
                        Callback<com.example.chatbot.OpenAI.ChatGPTRes> {
                        override fun onResponse(
                            call: Call<com.example.chatbot.OpenAI.ChatGPTRes>,
                            response: Response<com.example.chatbot.OpenAI.ChatGPTRes>
                        ) {
                            response.body()?.let { res ->
                                // 先儲存回傳的資料
                                res.choices.forEach { currentMessages.add(it.message) }
                                // 再儲存下次要傳送的資料
                                sendMessages.addAll(currentMessages)
                                // 刷新列表
                                msgAdapter.setterData(currentMessages)
                                CoroutineScope(Dispatchers.Main).launch {
                                    recyclerView.scrollToPosition(currentMessages.size - 1)
                                }
                                ll_progress.visibility = View.GONE //讀取完成，讀取條消失

                            }
                        }

                        override fun onFailure(
                            call: Call<com.example.chatbot.OpenAI.ChatGPTRes>,
                            t: Throwable
                        ) {
                            t.printStackTrace()
                            Method.logE(TAG, "onFailure: ${t.message}")
                        }
                    })
            }

        }
    }
}


