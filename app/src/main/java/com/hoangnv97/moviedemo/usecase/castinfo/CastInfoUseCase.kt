package com.hoangnv97.moviedemo.usecase.castinfo

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.CastInfo
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class CastInfoUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
) : FlowUseCase<CastInfo, Int>(ioDispatcher, ioCoroutineScope) {
    override val apiType: ApiEnum = ApiEnum.API_CAST_INFO

    override suspend fun buildUseCase(params: Int): CastInfo {
        return apiService.getCastInfo(params)
    }
}
