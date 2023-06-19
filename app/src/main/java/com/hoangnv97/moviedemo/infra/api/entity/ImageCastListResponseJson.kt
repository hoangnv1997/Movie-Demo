package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.ImageCast
import com.hoangnv97.moviedemo.domain.entity.ImageCastList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageCastListResponseJson(
    @Json(name = "profiles")
    val imageCastList: List<ImageCastResponseJson>
) {
    fun toEntity(): ImageCastList {
        return ImageCastList(
            imageCastList = imageCastList.map {
                it.toEntity()
            }
        )
    }
}

@JsonClass(generateAdapter = true)
data class ImageCastResponseJson(
    @Json(name = "file_path")
    val filePath: String
) {
    fun toEntity(): ImageCast {
        return ImageCast(
            filePath = filePath
        )
    }
}
