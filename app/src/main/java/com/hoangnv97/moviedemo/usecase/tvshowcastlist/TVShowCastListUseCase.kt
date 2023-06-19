package com.hoangnv97.moviedemo.usecase.tvshowcastlist

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.CastList
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class TVShowCastListUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
) : FlowUseCase<CastList, Int>(ioDispatcher, ioCoroutineScope) {

    override suspend fun buildUseCase(params: Int): CastList {
        return apiService.getTVShowCastList(params)
    }

    override val apiType: ApiEnum = ApiEnum.API_TV_SHOW_CAST_LIST
}
