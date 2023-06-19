package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.Genre
import com.hoangnv97.moviedemo.domain.entity.GenreList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreListResponseJson(
    @Json(name = "genres")
    val genreList: List<GenreResponseJson>
) {
    fun toEntity(): GenreList {
        return GenreList(genreList = this.genreList.map { it.toEntity() })
    }
}
@JsonClass(generateAdapter = true)
data class GenreResponseJson(
    val id: String,
    val name: String
) {
    fun toEntity(): Genre {
        return Genre(
            id = id,
            name = name.replace("Phim", "")
        )
    }
}
