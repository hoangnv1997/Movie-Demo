package com.hoangnv97.moviedemo.usecase.tvshowdetail

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class TVShowDetailUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
) : FlowUseCase<TVShow, Int>(ioDispatcher, ioCoroutineScope) {

    override suspend fun buildUseCase(params: Int): TVShow {
        return apiService.getTVShowDetail(params)
    }

    override val apiType: ApiEnum = ApiEnum.API_TV_SHOW_DETAIL
}
