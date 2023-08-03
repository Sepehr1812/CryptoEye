package com.nebka.cryptoeye.data.remote.api

import com.nebka.cryptoeye.data.remote.models.GeneralResponse
import com.nebka.cryptoeye.data.remote.models.TagResponse
import com.nebka.cryptoeye.data.remote.models.TagsRequestBody
import com.nebka.cryptoeye.data.remote.models.UpdateTimeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TagApi {

    @Headers("Authorization: Token 92335ad1961315d0f8db29d33b8ac05f5b080b44")
    @GET("v2/caches/latest_cache_updates")
    suspend fun getUpdateTime(): Response<GeneralResponse<UpdateTimeResponse>>

    @Headers(
        "Content-Type: application/json",
        "Authorization: Token 92335ad1961315d0f8db29d33b8ac05f5b080b44"
    )
    @POST("v2/tags")
    suspend fun getTagList(
        @Body tagsRequestBody: TagsRequestBody
    ): Response<GeneralResponse<List<TagResponse>>>
}