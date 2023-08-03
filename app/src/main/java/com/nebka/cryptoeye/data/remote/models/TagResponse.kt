package com.nebka.cryptoeye.data.remote.models

import com.squareup.moshi.Json

data class TagResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "logoUrl") val logoUrl: String
)
