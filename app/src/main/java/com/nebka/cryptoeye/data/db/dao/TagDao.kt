package com.nebka.cryptoeye.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nebka.cryptoeye.data.db.GeneralDao
import com.nebka.cryptoeye.data.db.entities.TagEntity


@Dao
interface TagDao : GeneralDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTagList(tagList: List<TagEntity>): Unit?

    @Query("SELECT * FROM tag ORDER BY id")
    suspend fun getTagList(): List<TagEntity>
}