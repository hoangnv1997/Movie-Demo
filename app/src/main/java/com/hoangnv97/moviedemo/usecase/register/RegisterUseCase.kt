package com.hoangnv97.moviedemo.usecase.register

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.api.params.RegisterRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class RegisterUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope
) : FlowUseCase<Boolean, RegisterRequest>(ioDispatcher, ioCoroutineScope) {

    override val apiType: ApiEnum = ApiEnum.API_REGISTER

    override suspend fun buildUseCase(params: RegisterRequest): Boolean {
        return apiService.register(params)
    }
}
