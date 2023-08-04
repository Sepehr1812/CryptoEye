package com.nebka.cryptoeye.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nebka.cryptoeye.data.db.dao.TagDao
import com.nebka.cryptoeye.data.db.entities.TagEntity


@Database(entities = [TagEntity::class], version = 1, exportSchema = false)
abstract class CryptoEyeDatabase : RoomDatabase() {

    abstract fun tagDao(): TagDao
}