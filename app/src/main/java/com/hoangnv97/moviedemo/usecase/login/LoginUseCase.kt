package com.hoangnv97.moviedemo.usecase.login

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.identitylocal.IdentityLocalRepository
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.api.params.LoginRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class LoginUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
    private val identityLocalRepository: IdentityLocalRepository,
) : FlowUseCase<Boolean, LoginRequest>(ioDispatcher, ioCoroutineScope) {

    override val apiType: ApiEnum = ApiEnum.API_LOGIN

    override suspend fun buildUseCase(params: LoginRequest): Boolean {
        return apiService.login(params)
    }

    suspend fun isLoginSuccess(email: String, password: String): Boolean {
        return withContext(ioDispatcher) {
            identityLocalRepository.isLoginSuccess(email, password)
        }
    }
}
