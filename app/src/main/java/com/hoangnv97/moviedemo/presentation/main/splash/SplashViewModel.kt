package com.hoangnv97.moviedemo.presentation.main.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangnv97.moviedemo.domain.appsettings.AppSettingsRepostory
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val appSettingsRepostory: AppSettingsRepostory) :
    BaseViewModel() {

    private val _isLoginValue = MutableLiveData<Boolean>()
    val isLoginValue: LiveData<Boolean> = _isLoginValue

    fun isLogin() {
        _isLoginValue.value = appSettingsRepostory.isLogin()
    }
}
