package com.example.chatbot.OpenAI
import com.google.gson.annotations.SerializedName

data class Msg(val content: String, val type: Int) {
    //定义静态成员
    companion object {
        const val RIGHT = 0
        const val LEFT = 1
    }
}
data class ShopMsg(val content: String, val type: Int) {
    //定义静态成员

}
data class CompletionResponse(
    @SerializedName("id") val id: String,
    @SerializedName("choices") val choices: List<Choices>
)
data class CompletionRequest(
    val model: String,
    val messages:ArrayList<Messages>,
    val temperature: Double,
    val stop: List<String>
)
data class Choices(
    @SerializedName("text") val text: String,
    @SerializedName("index") val index: Int,
    @SerializedName("logprobs") val logprobs: String,
    @SerializedName("finish_reason") val finishReason: String
)
data class OpenAiServiceRes(
    val id: String,
    val objects: String,
    val created : Int,
    val model: String,
    val usage: ArrayList<Usage>,
    val Messages: ArrayList<Messages>,
    val choices: ArrayList<choices>,
)
data class choices(
    val finish_reason: String,
    val index:Int
)