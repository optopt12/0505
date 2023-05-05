package com.example.chatbot.Network

import com.example.chatbot.BuildConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
interface ApiService {
    /**
     * Chat GPT API
     */
    @Headers("Accept-Encoding: identity","Content-Type: application/json","Authorization: Bearer ${BuildConfig.CHAT_GPT_KEY}")
    @POST("completions")
    fun sendChatGPT(@Body body: com.example.chatbot.OpenAI.ChatGPTReq): Call<com.example.chatbot.OpenAI.ChatGPTRes>

    /**
     * Google Places Auto Complete
     */
    @Headers("Accept-Encoding: identity")
    @GET("autocomplete/json")
    fun getPlacesAutoComplete(
        @Query("input") input: String,
        @Query("location") location: String, // Ex: 25.0338,121.5646
        @Query("components") component: String = "country:tw",
        @Query("radius") radius: String = "10000",
        @Query("type") type: String = "restaurant",
        @Query("key") key: String,
        @Query("language") language: String = "zh-TW"
    ): Call<com.example.chatbot.placesAutoComplete.AutoComplete>

    /**
     * Google Places Search
     */
    @Headers("Accept-Encoding: identity")
    @GET("nearbysearch/json")
    fun getPlaceSearch(
        @Query("location") location: String, // Ex: 25.0338,121.5646
        @Query("radius") radius: String = "1000", // Ex: 1000 公尺
        @Query("type") type: String = "restaurant",
        @Query("key") key: String,
        @Query("language") language: String = "zh-TW"
    ): Call<com.example.chatbot.placesSearch.PlacesSearch>

    @Headers("Accept-Encoding: identity")
    @GET("nearbysearch/json")
    fun getPlaceSearch(
        @Query("location") location: String, // Ex: 25.0338,121.5646
        @Query("radius") radius: String = "500", // Ex: 500 公尺
        @Query("keyword") keyword: String,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String,
        @Query("language") language: String = "zh-TW"
    ): Call<com.example.chatbot.placesSearch.PlacesSearch>

    /**
     * Google Places Search with keyword
     */
    @Headers("Accept-Encoding: identity")
    @GET("nearbysearch/json")
    fun getPlaceSearchWithKeyword(
        @Query("location") location: String, // Ex: 25.0338,121.5646
        @Query("radius") radius: Long = 1000L, // Ex: 1000 公尺
        @Query("keyword") keyword: String,
        @Query("key") key: String,
        @Query("language") language: String = "zh-TW"
    ): Call<com.example.chatbot.placesSearch.PlacesSearch>

    @Headers("Accept-Encoding: identity")
    @GET("nearbysearch/json")
    fun getPlaceSearchWithKeyword(
        @Query("location") location: String, // Ex: 25.0338,121.5646
        @Query("radius") radius: Long = 1000L, // Ex: 1000 公尺
        @Query("pagetoken") token: String,
        @Query("keyword") keyword: String,
        @Query("key") key: String,
        @Query("language") language: String = "zh-TW"
    ): Call<com.example.chatbot.placesSearch.PlacesSearch>

    /**
     * Google Places Details
     */
    @Headers("Accept-Encoding: identity")
    @GET("details/json")
    fun getPlaceDetails(
        @Query("place_id") placeID: String,
        @Query("language") language: String = "zh-TW",
        @Query("key") key: String
    ): Call<com.example.chatbot.placesDetails.PlacesDetails>
    /**
     * Google Places Photo
     */
    @Headers("Accept-Encoding: identity")
    @GET("photo")
    fun getPlacePhoto(
        @Query("photo_reference") photo_reference: String ,
        @Query("key") key: String
    ): Call<com.example.chatbot.placesDetails.PlacesDetails>
}