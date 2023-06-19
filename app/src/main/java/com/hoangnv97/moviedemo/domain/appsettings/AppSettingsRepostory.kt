package com.hoangnv97.moviedemo.domain.appsettings

interface AppSettingsRepostory {
    fun getAccessToken(): String?
    fun pushAccessToken(token: String?)
    fun clearAccessToken()

    fun isLogin(): Boolean
    fun setLogin(isLogin: Boolean)
}
