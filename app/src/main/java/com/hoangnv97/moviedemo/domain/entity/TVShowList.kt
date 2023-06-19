package com.hoangnv97.moviedemo.domain.entity

data class TVShowList(
    val page: Int,
    val results: List<TVShow>,
    val totalResults: Int,
    val totalPages: Int
)
