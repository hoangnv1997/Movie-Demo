package com.example.examplemovie.data.remote.entity

import com.example.examplemovie.domain.entity.PopularList
import com.hoangnv97.moviedemo.infra.api.entity.MovieResponseJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularListResponseJson(
    val page: String,
    val results: List<MovieResponseJson>,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int
) {
    fun toEntity(): PopularList {
        return PopularList(
            page = page,
            results = results.map {
                it.toEntity()
            },
            totalResults = totalResults,
            totalPages = totalPages,
        )
    }
}
