package com.nebka.cryptoeye.repos

import android.util.Log
import com.nebka.cryptoeye.data.db.dao.TagDao
import com.nebka.cryptoeye.data.db.executeQuery
import com.nebka.cryptoeye.data.mappers.TagLocalMapper
import com.nebka.cryptoeye.data.models.Tag
import javax.inject.Inject

class TagLocalRepository @Inject constructor(
    private val tagDao: TagDao
) {

    suspend fun addTagList(tagList: List<Tag>) = tagDao.executeQuery({
        addTagList(tagList.map { TagLocalMapper.fromDomain(it) })
    }, onError = { Log.d("Local::addTagList", it.toString()) })

    suspend fun getTagList() = tagDao.executeQuery({
        getTagList().map { TagLocalMapper.toDomain(it) }
    }, { Log.d("Local::getTagList", it.toString()) })
}