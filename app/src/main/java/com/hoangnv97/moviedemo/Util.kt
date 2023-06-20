package com.hoangnv97.moviedemo

import android.content.Context
import android.net.ConnectivityManager
import java.security.MessageDigest
import timber.log.Timber

class Util {
    companion object {
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val linkProperty = cm.getLinkProperties(cm.activeNetwork)
            Timber.e("$linkProperty")
            return linkProperty != null
        }
        fun appendZeroBeforeNumber(num: Int): String {
            return if (num < 10) "0$num" else num.toString()
        }
        fun createHash(target: String): String {
            return MessageDigest.getInstance("SHA-256")
                .digest(target.toByteArray())
                .joinToString(separator = "") {
                    "%02x".format(it)
                }
        }
    }
}
