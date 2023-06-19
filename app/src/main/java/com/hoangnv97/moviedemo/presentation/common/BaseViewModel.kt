package com.hoangnv97.moviedemo.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

open class BaseViewModel : ViewModel() {
    val _loading = MutableStateFlow(false)
    private val _failed = MutableStateFlow<ErrorObj?>(null)
    val failed: StateFlow<ErrorObj?> = _failed.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    fun setFailed(errorObj: ErrorObj?) {
        _failed.value = errorObj
    }

    fun setLoading(value: Boolean) {
        _loading.value = value
    }
}
