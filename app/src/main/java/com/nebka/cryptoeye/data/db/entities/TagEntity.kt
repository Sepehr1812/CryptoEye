package com.nebka.cryptoeye.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tag")
data class TagEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val symbol: String,
    val logoUrl: String
)
