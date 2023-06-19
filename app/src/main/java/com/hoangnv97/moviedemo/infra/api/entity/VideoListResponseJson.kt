package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.Video
import com.hoangnv97.moviedemo.domain.entity.VideoList
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoListResponseJson(
    val results: List<VideoResponseJson>
) {
    fun toEntity(): VideoList {
        return VideoList(
            results = results.map {
                it.toEntity()
            }
        )
    }
}
@JsonClass(generateAdapter = true)
data class VideoResponseJson(
    val id: String,
    val key: String,
    val name: String
) {
    fun toEntity(): Video {
        return Video(
            id = id,
            key = key,
            name = name
        )
    }
}
