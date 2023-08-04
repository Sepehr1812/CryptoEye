package com.nebka.cryptoeye.repos

import android.util.Log
import com.nebka.cryptoeye.data.mappers.TagRemoteMapper
import com.nebka.cryptoeye.data.remote.api.TagApi
import com.nebka.cryptoeye.data.remote.executeApi
import com.nebka.cryptoeye.data.remote.models.TagsRequestBody
import javax.inject.Inject

class TagRemoteRepository @Inject constructor(
    private val tagApi: TagApi
) {

    suspend fun getUpdateTime() = executeApi({
        tagApi.getUpdateTime()
    }, caster = {
        it.result.tagsUpdateTime
    }, onError = {
        Log.d("Remote::getUpdateTime", it.toString())
    })

    suspend fun getTagList() = executeApi({
        tagApi.getTagList(
            TagsRequestBody(
                limit = 1000,
                fromId = 0,
                fromDate = "2019-08-24"
            )
        )
    }, caster = {
        it.result.map { tagResponse -> TagRemoteMapper.toDomain(tagResponse) }
    }, onError = {
        Log.d("Remote::getTagList", it.toString())
    })
}