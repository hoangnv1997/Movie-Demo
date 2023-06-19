package com.example.examplemovie.domain.entity

import com.hoangnv97.moviedemo.domain.entity.Movie

data class PopularList(
    val page: String,
    val results: List<Movie>,
    val totalResults: Int,
    val totalPages: Int
)
