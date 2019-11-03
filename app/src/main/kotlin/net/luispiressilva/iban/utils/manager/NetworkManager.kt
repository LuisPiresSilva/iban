package net.luispiressilva.iban.utils.manager

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkManager @Inject constructor() {
    fun isNetworkAvailable(ctx: Context?): Boolean {
        val connectivityManager = ctx?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        return false
    }
}