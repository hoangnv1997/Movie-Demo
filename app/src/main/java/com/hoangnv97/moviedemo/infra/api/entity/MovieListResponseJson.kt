package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.domain.entity.MovieList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListResponseJson(
    val page: String,
    val results: List<MovieResponseJson>,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int
) {
    fun toEntity(): MovieList {
        return MovieList(
            page = page,
            results = results.map {
                it.toEntity()
            },
            totalResults = totalResults,
            totalPages = totalPages,
        )
    }
}
@JsonClass(generateAdapter = true)
data class MovieResponseJson(
    @Json(name = "poster_path") val posterPath: String?,
    val adult: Boolean?,
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val popularity: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val title: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    val name: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    val runtime: Int?,
    val genres: List<GenreResponseJson>?,
) {
    fun toEntity(): Movie {
        return Movie(
            posterPath = posterPath,
            adult = adult,
            overview = overview,
            releaseDate = releaseDate,
            genreIds = genreIds,
            id = id,
            originalTitle = originalTitle,
            originalLanguage = originalLanguage,
            backdropPath = backdropPath,
            popularity = popularity,
            voteCount = voteCount,
            video = video,
            voteAverage = voteAverage,
            title = title,
            firstAirDate = firstAirDate,
            name = name,
            originalName = originalName,
            originCountry = originCountry,
            runtime = runtime,
            genres = genres?.map {
                it.toEntity()
            }
        )
    }
}
