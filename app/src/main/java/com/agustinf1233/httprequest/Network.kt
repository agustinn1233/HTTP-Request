package com.agustinf1233.httprequest

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class Network {
    companion object {

        fun checkRed(activity:AppCompatActivity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo!= null && networkInfo.isConnected
        }
    }
}