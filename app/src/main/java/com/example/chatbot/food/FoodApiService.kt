package com.example.chatbot

import com.example.chatbot.food.Posts
import com.example.chatbot.food.Test
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


interface FoodApiService {
    //    @GET("https://api.edamam.com/api/food-database/v2/parser?app_id=ed8896ab&app_key=35488a643aa2e7b7691d085b31de8587&ingr=1%20whole%20chicken&nutrition-type=cooking\n")
    @GET("https://api.edamam.com/api/food-database/v2/parser")
    fun index(
        @QueryMap data: Map<String, String,>
    ): Call<Posts>

    @POST("/api/food-database/v2/nutrients")
    fun a(@Body data : Test):Call<Test>


}



