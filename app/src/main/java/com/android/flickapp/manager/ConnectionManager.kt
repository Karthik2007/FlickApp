package com.android.flickapp.manager

import android.content.Context
import android.net.ConnectivityManager

class ConnectionManager {

    companion object {

        /**
         * checks the whether device is connected to network
         * @param ctx
         * @return
         */
        fun isConnected(ctx: Context): Boolean {
            val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}