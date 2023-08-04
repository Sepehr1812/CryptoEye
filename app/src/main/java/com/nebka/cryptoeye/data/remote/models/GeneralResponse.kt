package com.nebka.cryptoeye.data.remote.models

import com.squareup.moshi.Json

data class GeneralResponse<T : Any>(
    @Json(name = "result") val result: T
)