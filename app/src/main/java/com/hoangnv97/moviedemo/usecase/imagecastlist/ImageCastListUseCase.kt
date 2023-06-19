package com.hoangnv97.moviedemo.usecase.imagecastlist

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.ImageCastList
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class ImageCastListUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
) : FlowUseCase<ImageCastList, Int>(ioDispatcher, ioCoroutineScope) {
    override val apiType: ApiEnum = ApiEnum.API_IMAGE_CAST_LIST

    override suspend fun buildUseCase(params: Int): ImageCastList {
        return apiService.getImageCastList(params)
    }
}
