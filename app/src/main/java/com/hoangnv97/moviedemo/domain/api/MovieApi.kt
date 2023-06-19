package com.hoangnv97.moviedemo.domain.api

import com.example.examplemovie.data.remote.entity.PopularListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.CastInfoResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.CastListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.ComingSoonListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.DiscoverMovieListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.GenreListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.ImageCastListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.MovieListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.MovieResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.RelatedMovieListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.TVShowListResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.TVShowResponseJson
import com.hoangnv97.moviedemo.infra.api.entity.VideoListResponseJson
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularList(@Query("page") page: Int): PopularListResponseJson

    @GET("movie/now_playing")
    suspend fun getMovieList(@Query("page") page: Int): MovieListResponseJson

    @GET("movie/upcoming")
    suspend fun getComingSoonList(@Query("page") page: Int): ComingSoonListResponseJson

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreListResponseJson

    @GET("discover/movie")
    suspend fun getDiscoverMovieList(@Query("page") page: Int): DiscoverMovieListResponseJson

    @GET("discover/tv")
    suspend fun getDiscoverTVShowList(@Query("page") page: Int): TVShowListResponseJson

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): MovieResponseJson

    @Headers("isUs: true")
    @GET("movie/{id}/videos")
    suspend fun getVideoList(@Path("id") id: Int): VideoListResponseJson

    @GET("movie/{id}/credits")
    suspend fun getCastList(@Path("id") id: Int): CastListResponseJson

    @Headers("isUs: true")
    @GET("person/{id}")
    suspend fun getCastInfo(@Path("id") id: Int): CastInfoResponseJson

    @GET("person/{id}/images")
    suspend fun getImageCastList(@Path("id") id: Int): ImageCastListResponseJson

    @GET("person/{id}/combined_credits")
    suspend fun getRelatedMovieList(@Path("id") id: Int): RelatedMovieListResponseJson

    @GET("tv/{id}")
    suspend fun getTVShowDetail(@Path("id") id: Int): TVShowResponseJson

    @Headers("isUs: true")
    @GET("tv/{id}/videos")
    suspend fun getTVShowVideoList(@Path("id") id: Int): VideoListResponseJson

    @GET("tv/{id}/credits")
    suspend fun getTVShowCastList(@Path("id") id: Int): CastListResponseJson
}
