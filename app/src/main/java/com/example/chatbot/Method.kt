package com.example.chatbot

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.viewbinding.BuildConfig
import com.example.chatbot.Constants.PERMISSION_CODE

/**
 * @author 麥光廷
 * @date 2023-03-10
 * 存放一些常用到的方法。
 */
object Method {
    /**
     * Logcat
     */
    fun logE(tag: String, message: String) {
        if (BuildConfig.DEBUG)
            Log.e(tag, message)
    }

    fun logD(tag: String, message: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message)
    }

    /**
     * Permissions
     */
    fun requestPermission(activity: Activity, vararg permissions: String): Boolean {
        return if (!hasPermissions(activity, *permissions)) {
            requestPermissions(activity, permissions, PERMISSION_CODE)
            false
        } else
            true
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            )
                return false
        return true
    }
}