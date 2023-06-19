package com.hoangnv97.moviedemo.domain.entity

data class MovieList(
    val page: String,
    val results: List<Movie>,
    val totalResults: Int,
    val totalPages: Int
)
