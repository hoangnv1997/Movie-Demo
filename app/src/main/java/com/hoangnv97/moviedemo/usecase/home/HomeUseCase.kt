package com.hoangnv97.moviedemo.usecase.home

import androidx.paging.PagingData
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.Movie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class HomeUseCase @Inject constructor(private val apiService: ApiService) {

    fun getPopularList(pageSize: Int): Flow<PagingData<Movie>> {
        return apiService.getPopularList(pageSize)
    }

    fun getMovieList(pageSize: Int): Flow<PagingData<Movie>> {
        return apiService.getMovieList(pageSize)
    }

    fun getComingSoonList(pageSize: Int): Flow<PagingData<Movie>> {
        return apiService.getComingSoonList(pageSize)
    }
}
