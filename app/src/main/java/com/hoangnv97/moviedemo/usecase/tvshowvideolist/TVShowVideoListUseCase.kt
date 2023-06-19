package com.hoangnv97.moviedemo.usecase.tvshowvideolist

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.VideoList
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class TVShowVideoListUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
) : FlowUseCase<VideoList, Int>(ioDispatcher, ioCoroutineScope) {

    override suspend fun buildUseCase(params: Int): VideoList {
        return apiService.getTVShowVideoList(params)
    }

    override val apiType: ApiEnum = ApiEnum.API_TV_SHOW_VIDEO_LIST
}
