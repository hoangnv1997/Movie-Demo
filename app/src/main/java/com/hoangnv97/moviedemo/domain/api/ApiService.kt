package com.hoangnv97.moviedemo.domain.api

import androidx.paging.PagingData
import com.hoangnv97.moviedemo.domain.entity.CastInfo
import com.hoangnv97.moviedemo.domain.entity.CastList
import com.hoangnv97.moviedemo.domain.entity.GenreList
import com.hoangnv97.moviedemo.domain.entity.ImageCastList
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.domain.entity.RelatedMovieList
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.domain.entity.VideoList
import com.hoangnv97.moviedemo.infra.api.params.LoginRequest
import com.hoangnv97.moviedemo.infra.api.params.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun register(request: RegisterRequest): Boolean
    suspend fun login(request: LoginRequest): Boolean
    fun getPopularList(pageSize: Int): Flow<PagingData<Movie>>
    fun getMovieList(pageSize: Int): Flow<PagingData<Movie>>
    fun getComingSoonList(pageSize: Int): Flow<PagingData<Movie>>
    suspend fun getGenreList(): GenreList
    fun getDiscoverMovieList(pageSize: Int): Flow<PagingData<Movie>>
    fun getDiscoverTVShowList(pageSize: Int): Flow<PagingData<TVShow>>
    suspend fun getMovieDetail(id: Int): Movie
    suspend fun getVideoList(id: Int): VideoList
    suspend fun getCastList(id: Int): CastList
    suspend fun getCastInfo(id: Int): CastInfo
    suspend fun getImageCastList(id: Int): ImageCastList
    suspend fun getRelatedMovieList(id: Int): RelatedMovieList
    suspend fun getTVShowDetail(id: Int): TVShow
    suspend fun getTVShowVideoList(id: Int): VideoList
    suspend fun getTVShowCastList(id: Int): CastList
}
