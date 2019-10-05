package com.ogi.submission4.moviereview.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Network
{
    companion object{
        fun isInternetAvailable(context: Context): Boolean{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                return  activeNetwork?.isConnected?:false
        }
    }
}