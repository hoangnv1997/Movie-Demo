package com.hoangnv97.moviedemo.usecase.discovermovies

import androidx.paging.PagingData
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.Movie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class DiscoverMovieListUseCase @Inject constructor(private val apiService: ApiService) {

    fun getDiscoverMovieList(pageSize: Int): Flow<PagingData<Movie>> {
        return apiService.getDiscoverMovieList(pageSize)
    }
}
