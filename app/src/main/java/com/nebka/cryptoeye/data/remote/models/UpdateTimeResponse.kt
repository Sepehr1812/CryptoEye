package com.nebka.cryptoeye.data.remote.models

import com.squareup.moshi.Json

data class UpdateTimeResponse(
    @Json(name = "tagsUpdateTime") val tagsUpdateTime: Long
)
