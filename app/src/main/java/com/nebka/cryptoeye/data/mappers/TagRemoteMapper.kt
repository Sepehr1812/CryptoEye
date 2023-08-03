package com.nebka.cryptoeye.data.mappers

import com.nebka.cryptoeye.data.models.Tag
import com.nebka.cryptoeye.data.remote.models.TagResponse

object TagRemoteMapper {

    fun toDomain(tagResponse: TagResponse) = tagResponse.run { Tag(id, name, symbol, logoUrl) }
}