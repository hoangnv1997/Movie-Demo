package com.hoangnv97.moviedemo.presentation.main.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.api.LocalException
import com.hoangnv97.moviedemo.domain.api.LocalExceptionStatus
import com.hoangnv97.moviedemo.domain.appsettings.AppSettingsRepostory
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.api.params.LoginRequest
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appSettingsRepostory: AppSettingsRepostory,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    val email = MutableStateFlow<String?>(null)
    val password = MutableStateFlow<String?>(null)

    private fun isEmailValid(): Boolean {
        return email.value?.let {
            Patterns.EMAIL_ADDRESS.matcher(it).matches()
        } ?: kotlin.run {
            false
        }
    }

    private fun isPasswordValid(): Boolean {
        return password.value?.let {
            it.length > 8
        } ?: kotlin.run {
            false
        }
    }

    private val _login = MutableStateFlow(false)
    val login: StateFlow<Boolean> = _login.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    val loading = combine(loginUseCase.loading, _loading) { list ->
        list.reduce { a, b ->
            a || b
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    fun login() {
        viewModelScope.launch {
            if (!isEmailValid() || !isPasswordValid()) {
                val exception = LocalException(
                    status = LocalExceptionStatus.INVALID_LOGIN,
                    title = "Login failed",
                    message = "Your email or password is not the correct format."
                )
                val error = ErrorObj(
                    api = ApiEnum.API_LOCAL,
                    throwable = exception
                )
                setFailed(error)
                return@launch
            }
            val request = LoginRequest(
                email = email.value!!,
                password = password.value!!
            )
            loginUseCase.execute(request).collect {
                if (it) {
                    appSettingsRepostory.setLogin(it)
                    _login.value = true
                } else {
                    val exception = LocalException(
                        status = LocalExceptionStatus.INVALID_LOGIN,
                        title = "Login failed",
                        message = "Email or password is incorrect. Please try again."
                    )
                    val error = ErrorObj(
                        api = ApiEnum.API_LOCAL,
                        throwable = exception
                    )
                    setFailed(error)
                }
            }
        }
    }
}
