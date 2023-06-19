package com.hoangnv97.moviedemo.infra.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.api.MovieApi
import com.hoangnv97.moviedemo.domain.entity.CastInfo
import com.hoangnv97.moviedemo.domain.entity.CastList
import com.hoangnv97.moviedemo.domain.entity.GenreList
import com.hoangnv97.moviedemo.domain.entity.ImageCastList
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.domain.entity.RelatedMovieList
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.domain.entity.VideoList
import com.hoangnv97.moviedemo.infra.api.pagingsource.ComingSoonPagingSource
import com.hoangnv97.moviedemo.infra.api.pagingsource.DiscoverMoviesPagingSource
import com.hoangnv97.moviedemo.infra.api.pagingsource.DiscoverTVShowPagingSource
import com.hoangnv97.moviedemo.infra.api.pagingsource.MoviePagingSource
import com.hoangnv97.moviedemo.infra.api.pagingsource.PopularPagingSource
import com.hoangnv97.moviedemo.infra.api.params.LoginRequest
import com.hoangnv97.moviedemo.infra.api.params.RegisterRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ApiServiceImpl @Inject constructor(private val api: MovieApi) : ApiService {

    override suspend fun register(request: RegisterRequest): Boolean {
        // fake
        return true
    }

    override suspend fun login(request: LoginRequest): Boolean {
        // fake
        return true
    }

    override fun getPopularList(pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            PopularPagingSource(api = api)
        }.flow
    }

    override fun getMovieList(pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            MoviePagingSource(api = api)
        }.flow
    }

    override fun getComingSoonList(pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            ComingSoonPagingSource(api = api)
        }.flow
    }

    override suspend fun getGenreList(): GenreList {
        return api.getGenreList().toEntity()
    }

    override fun getDiscoverMovieList(pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            DiscoverMoviesPagingSource(api = api)
        }.flow
    }

    override fun getDiscoverTVShowList(pageSize: Int): Flow<PagingData<TVShow>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            DiscoverTVShowPagingSource(api = api)
        }.flow
    }

    override suspend fun getMovieDetail(id: Int): Movie {
        return api.getMovieDetail(id).toEntity()
    }

    override suspend fun getVideoList(id: Int): VideoList {
        return api.getVideoList(id).toEntity()
    }

    override suspend fun getCastList(id: Int): CastList {
        return api.getCastList(id).toEntity()
    }

    override suspend fun getCastInfo(id: Int): CastInfo {
        return api.getCastInfo(id).toEntity()
    }

    override suspend fun getImageCastList(id: Int): ImageCastList {
        return api.getImageCastList(id).toEntity()
    }

    override suspend fun getRelatedMovieList(id: Int): RelatedMovieList {
        return api.getRelatedMovieList(id).toEntity()
    }

    override suspend fun getTVShowDetail(id: Int): TVShow {
        return api.getTVShowDetail(id).toEntity()
    }

    override suspend fun getTVShowVideoList(id: Int): VideoList {
        return api.getTVShowVideoList(id).toEntity()
    }

    override suspend fun getTVShowCastList(id: Int): CastList {
        return api.getTVShowCastList(id).toEntity()
    }
}
