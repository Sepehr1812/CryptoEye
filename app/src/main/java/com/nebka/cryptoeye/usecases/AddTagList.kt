package com.nebka.cryptoeye.usecases

import com.nebka.cryptoeye.data.db.DatabaseResult
import com.nebka.cryptoeye.data.models.Tag
import com.nebka.cryptoeye.repos.TagLocalRepository
import javax.inject.Inject

class AddTagList @Inject constructor(private val tagLocalRepository: TagLocalRepository) :
    GeneralUseCase<AddTagList.RequestValues, DatabaseResult<Unit>>() {

    data class RequestValues(val tagList: List<Tag>) : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        tagLocalRepository.addTagList(requestValues.tagList)
}