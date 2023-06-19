package com.hoangnv97.moviedemo.domain.entity

data class CastList(
    val castList: List<Cast>
)
data class Cast(
    val id: Int,
    val name: String,
    val profilePath: String?
)
