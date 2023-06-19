package com.hoangnv97.moviedemo.usecase.relatedmovielist

import com.hoangnv97.moviedemo.common.FlowUseCase
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.RelatedMovieList
import com.hoangnv97.moviedemo.infra.ApiEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class RelatedMovieListUseCase @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope ioCoroutines: CoroutineScope,
) : FlowUseCase<RelatedMovieList, Int>(ioDispatcher, ioCoroutines) {

    override suspend fun buildUseCase(params: Int): RelatedMovieList {
        return apiService.getRelatedMovieList(params)
    }

    override val apiType: ApiEnum = ApiEnum.API_RELATED_MOVIE
}
