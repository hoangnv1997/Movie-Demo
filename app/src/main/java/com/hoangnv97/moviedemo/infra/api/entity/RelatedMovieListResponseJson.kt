package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.RelatedMovie
import com.hoangnv97.moviedemo.domain.entity.RelatedMovieList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedMovieListResponseJson(
    val cast: List<RelatedMovieResponseJson>
) {
    fun toEntity(): RelatedMovieList {
        return RelatedMovieList(
            relatedMovieList = cast.map {
                it.toEntity()
            }
        )
    }
}

@JsonClass(generateAdapter = true)
data class RelatedMovieResponseJson(
    val id: Int,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "original_name")
    val name: String?,
    @Json(name = "original_title")
    val title: String?
) {
    fun toEntity(): RelatedMovie {
        return RelatedMovie(
            id = id,
            mediaType = mediaType,
            posterPath = posterPath,
            title = name ?: title!!
        )
    }
}
