package com.example.chatbot

/**
 * @author 麥光廷
 * @date 2023-03-10
 * 存放一些常用到的靜態變數。
 */
object Constants {
    /**
     * Permission Code
     */
    const val PERMISSION_CODE = 1001

    /**
     * Permission
     */
    const val PERMISSION_FINE_LOCATION =  android.Manifest.permission.ACCESS_FINE_LOCATION
    const val PERMISSION_COARSE_LOCATION =  android.Manifest.permission.ACCESS_COARSE_LOCATION

    val location_permission = arrayOf(PERMISSION_FINE_LOCATION, PERMISSION_COARSE_LOCATION)
}