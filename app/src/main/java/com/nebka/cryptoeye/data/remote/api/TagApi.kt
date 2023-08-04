package com.nebka.cryptoeye.data.remote.api

import com.nebka.cryptoeye.data.remote.models.GeneralResponse
import com.nebka.cryptoeye.data.remote.models.TagArrayResponse
import com.nebka.cryptoeye.data.remote.models.TagsRequestBody
import com.nebka.cryptoeye.data.remote.models.UpdateTimeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TagApi {

    @GET("v2/caches/latest_cache_updates")
    suspend fun getUpdateTime(): Response<GeneralResponse<UpdateTimeResponse>>

    @POST("v2/tags")
    suspend fun getTagList(
        @Body tagsRequestBody: TagsRequestBody
    ): Response<GeneralResponse<TagArrayResponse>>
}