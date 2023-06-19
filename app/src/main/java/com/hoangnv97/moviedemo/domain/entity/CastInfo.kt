package com.hoangnv97.moviedemo.domain.entity

data class CastInfo(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val knownForDepartment: String,
    val biography: String
)

data class ImageCastList(
    val imageCastList: List<ImageCast>
)

data class ImageCast(
    val filePath: String
)
