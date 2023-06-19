package com.hoangnv97.moviedemo.domain.entity

data class RelatedMovieList(
    val relatedMovieList: List<RelatedMovie>
)

data class RelatedMovie(
    val id: Int,
    val mediaType: String,
    val posterPath: String?,
    val title: String
)
