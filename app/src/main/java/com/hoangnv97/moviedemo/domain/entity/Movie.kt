package com.hoangnv97.moviedemo.domain.entity

data class Movie(
    val posterPath: String?,
    val adult: Boolean?,
    val overview: String?,
    val releaseDate: String?,
    val genreIds: List<Int>?,
    val id: Int,
    val originalTitle: String?,
    val originalLanguage: String?,
    val backdropPath: String?,
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean?,
    val voteAverage: Double,
    val title: String?,
    val firstAirDate: String?,
    val name: String?,
    val originalName: String?,
    val originCountry: List<String>?,
    val runtime: Int?,
    val genres: List<Genre>?,
)

data class Genre(
    val id: String,
    val name: String
)

enum class MovieType {
    POPULAR {
        override fun toString(): String {
            return "Popular"
        }
    },
    MOVIES {
        override fun toString(): String {
            return "Movies"
        }
    },
    COMING_SOON {
        override fun toString(): String {
            return "Coming soon"
        }
    },
    UNKNOWN
}
