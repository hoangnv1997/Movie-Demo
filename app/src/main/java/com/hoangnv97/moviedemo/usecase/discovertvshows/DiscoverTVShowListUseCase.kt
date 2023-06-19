package com.hoangnv97.moviedemo.usecase.discovertvshows

import androidx.paging.PagingData
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.entity.TVShow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class DiscoverTVShowListUseCase @Inject constructor(private val apiService: ApiService) {

    fun getDiscoverTVShowList(pageSize: Int): Flow<PagingData<TVShow>> {
        return apiService.getDiscoverTVShowList(pageSize)
    }
}
