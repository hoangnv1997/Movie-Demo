package com.hoangnv97.moviedemo.infra.api.entity

import com.hoangnv97.moviedemo.domain.entity.CastInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastInfoResponseJson(
    val id: Int,
    val name: String,
    @Json(name = "profile_path")
    val profilePath: String?,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val biography: String
) {
    fun toEntity(): CastInfo {
        return CastInfo(
            id = id,
            name = name,
            profilePath = profilePath,
            knownForDepartment = knownForDepartment,
            biography = biography
        )
    }
}
