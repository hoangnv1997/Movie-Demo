package com.hoangnv97.moviedemo.usecase.login

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.api.params.LoginRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class LoginUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope
) : FlowUseCase<Boolean, LoginRequest>(ioDispatcher, ioCoroutineScope) {

    override val apiType: ApiEnum = ApiEnum.API_LOGIN

    override suspend fun buildUseCase(params: LoginRequest): Boolean {
        return apiService.login(params)
    }
}
