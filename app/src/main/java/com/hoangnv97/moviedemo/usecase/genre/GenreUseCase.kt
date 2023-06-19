package com.hoangnv97.moviedemo.usecase.genre

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.GenreList
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class GenreUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutineScope: CoroutineScope,
) : FlowUseCase<GenreList, Unit?>(ioDispatcher, ioCoroutineScope) {
    override val apiType: ApiEnum = ApiEnum.API_GENRE_LIST

    override suspend fun buildUseCase(params: Unit?): GenreList {
        return apiService.getGenreList()
    }
}
