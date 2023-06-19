package com.hoangnv97.moviedemo.infra.appsettings

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.hoangnv97.moviedemo.domain.appsettings.AppSettingsRepostory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppSettingsRepostory {

    enum class Key {
        ACCESS_TOKEN,
        IS_LOGIN
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
    }

    private fun isSaved(key: Key): Boolean = prefs.contains(key.name)

    private fun getString(key: Key, defaultValue: String?): String? =
        if (isSaved(key)) prefs.getString(key.name, defaultValue) else defaultValue

    private fun saveString(key: Key, value: String?) {
        prefs.edit().putString(key.name, value).apply()
    }

    private fun getBoolean(key: Key, defaultValue: Boolean): Boolean =
        prefs.getBoolean(key.name, defaultValue)

    private fun saveBoolean(key: Key, value: Boolean) {
        prefs.edit().putBoolean(key.name, value).apply()
    }

    override fun getAccessToken(): String? = getString(Key.ACCESS_TOKEN, "")

    override fun pushAccessToken(token: String?) {
        saveString(Key.ACCESS_TOKEN, token)
    }

    override fun clearAccessToken() {
        saveString(Key.ACCESS_TOKEN, null)
    }

    override fun isLogin(): Boolean = getBoolean(Key.IS_LOGIN, false)

    override fun setLogin(isLogin: Boolean) {
        saveBoolean(Key.IS_LOGIN, isLogin)
    }
}
