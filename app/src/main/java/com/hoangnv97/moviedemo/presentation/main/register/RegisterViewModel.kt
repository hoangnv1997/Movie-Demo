package com.hoangnv97.moviedemo.presentation.main.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.api.LocalException
import com.hoangnv97.moviedemo.domain.api.LocalExceptionStatus
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.api.params.RegisterRequest
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
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

    private val _register = MutableStateFlow(false)
    val register: StateFlow<Boolean> = _register.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    val loading = combine(registerUseCase.loading, _loading) { list ->
        list.reduce { a, b ->
            a || b
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    fun register() {
        viewModelScope.launch {
            if (!isEmailValid() || !isPasswordValid()) {
                val exception = LocalException(
                    status = LocalExceptionStatus.INVALID_REGISTER,
                    title = "Registration failed",
                    message = "Your email or password is not in the correct format."
                )
                val error = ErrorObj(api = ApiEnum.API_LOCAL, throwable = exception)
                setFailed(error)
                return@launch
            }
            val request = RegisterRequest(
                email = email.value!!,
                password = password.value!!,
            )
            registerUseCase.execute(params = request).collect {
                if (it) {
                    _register.value = true
                } else {
                    val exception = LocalException(
                        status = LocalExceptionStatus.INVALID_REGISTER,
                        title = "Registration failed",
                        message = "An error occurred, please try again",
                    )
                    val error = ErrorObj(api = ApiEnum.API_LOCAL, throwable = exception)
                    setFailed(error)
                }
            }
            registerUseCase.registerLocalAccount(
                email = email.value!!,
                password = password.value!!,
            )
        }
    }
}
