package com.example.chatbot.placesDetails

import android.net.Uri
import android.os.Parcelable
import com.example.chatbot.Adapter.NestedData
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Url

@Parcelize
data class data(
    val photoList: MutableList<String>,
    val image: String,
    val formatted_address: String,
    val name: String,
    val formatted_phone_number: String,
    val author_name: MutableList<String>,
    val language: MutableList<String>,
    val text: MutableList<String>,
    val profile_photo_url: MutableList<String>
) : Parcelable


