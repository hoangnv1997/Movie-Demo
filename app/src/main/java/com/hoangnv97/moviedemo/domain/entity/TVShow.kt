package com.hoangnv97.moviedemo.domain.entity

data class TVShow(
    val posterPath: String?,
    val genreIds: List<Int>?,
    val id: Int,
    val backdropPath: String?,
    val voteAverage: Float,
    val name: String?,
    val originalName: String?,
    val voteCount: Int? = 0,
    val genres: List<Genre>?,
    val overview: String?,
    val numberOfEpisodes: Int? = 0,
    val numberOfSeasons: Int? = 0,
    val firstAirDate: String?,
)
