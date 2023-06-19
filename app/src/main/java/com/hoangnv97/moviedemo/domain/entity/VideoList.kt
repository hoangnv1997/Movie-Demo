package com.hoangnv97.moviedemo.domain.entity

data class VideoList(
    val results: List<Video>
)

data class Video(
    val id: String,
    val key: String,
    val name: String
)
