package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.ComingSoonList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComingSoonListResponseJson(
    val page: String,
    val results: List<MovieResponseJson>,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int
) {
    fun toEntity(): ComingSoonList {
        return ComingSoonList(
            page = page,
            results = results.map {
                it.toEntity()
            },
            totalResults = totalResults,
            totalPages = totalPages,
        )
    }
}
