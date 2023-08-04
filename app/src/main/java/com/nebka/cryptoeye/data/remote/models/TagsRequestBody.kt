package com.nebka.cryptoeye.data.remote.models

import com.squareup.moshi.Json

data class TagsRequestBody(
    @Json(name = "limit") val limit: Int,
    @Json(name = "from_id") val fromId: Long,
    @Json(name = "from_date") val fromDate: String
)
