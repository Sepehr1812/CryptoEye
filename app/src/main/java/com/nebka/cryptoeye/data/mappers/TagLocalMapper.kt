package com.nebka.cryptoeye.data.mappers

import com.nebka.cryptoeye.data.db.entities.TagEntity
import com.nebka.cryptoeye.data.models.Tag

object TagLocalMapper {

    fun toDomain(tagEntity: TagEntity) = tagEntity.run { Tag(id, name, symbol, logoUrl) }

    fun fromDomain(tag: Tag) = tag.run { TagEntity(id, name, symbol, logoUrl) }
}