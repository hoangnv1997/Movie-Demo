package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.Cast
import com.hoangnv97.moviedemo.domain.entity.CastList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastListResponseJson(
    @Json(name = "cast")
    val casts: List<CastResponseJson>
) {
    fun toEntity(): CastList {
        return CastList(
            castList = casts.map {
                it.toEntity()
            }
        )
    }
}

@JsonClass(generateAdapter = true)
data class CastResponseJson(
    val id: Int,
    val name: String,
    @Json(name = "profile_path")
    val profilePath: String?
) {
    fun toEntity(): Cast {
        return Cast(
            id = id,
            name = name,
            profilePath = profilePath,
        )
    }
}
