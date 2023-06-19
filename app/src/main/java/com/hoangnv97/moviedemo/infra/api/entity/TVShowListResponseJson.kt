package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.Genre
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.domain.entity.TVShowList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVShowListResponseJson(
    val page: Int,
    val results: List<TVShowResponseJson>,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int
) {
    fun toEntity(): TVShowList {
        return TVShowList(
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
data class TVShowResponseJson(
    // test
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Float,
    val name: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "vote_count")
    val voteCount: Int? = 0,
    val genres: List<Genre>?,
    val overview: String?,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int? = 0,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int? = 0,
    @Json(name = "first_air_date")
    val firstAirDate: String?
) {
    fun toEntity(): TVShow {
        return TVShow(
            posterPath = posterPath,
            genreIds = genreIds,
            id = id,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            name = name,
            originalName = originalName,
            voteCount = voteCount,
            genres = genres,
            overview = overview,
            numberOfEpisodes = numberOfEpisodes,
            numberOfSeasons = numberOfSeasons,
            firstAirDate = firstAirDate
        )
    }
}
