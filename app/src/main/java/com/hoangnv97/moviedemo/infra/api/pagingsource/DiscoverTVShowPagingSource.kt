package com.hoangnv97.moviedemo.infra.api.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hoangnv97.moviedemo.domain.api.MovieApi
import com.hoangnv97.moviedemo.domain.entity.TVShow
import kotlinx.coroutines.delay

class DiscoverTVShowPagingSource(private val api: MovieApi) : PagingSource<Int, TVShow>() {

    override fun getRefreshKey(state: PagingState<Int, TVShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShow> {
        return try {
            val nextPage = params.key ?: 1
            val response = api.getDiscoverTVShowList(nextPage).toEntity()
            delay(1000)
            LoadResult.Page(
                data = response.results,
                prevKey = null, nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
