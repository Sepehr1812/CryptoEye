package com.nebka.cryptoeye.data.remote.models

import com.squareup.moshi.Json

data class TagArrayResponse(
    @Json(name = "tags") val tags: List<TagResponse>
)
