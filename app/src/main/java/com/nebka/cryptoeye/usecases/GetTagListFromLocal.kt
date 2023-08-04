package com.nebka.cryptoeye.usecases

import com.nebka.cryptoeye.data.db.DatabaseResult
import com.nebka.cryptoeye.data.models.Tag
import com.nebka.cryptoeye.repos.TagLocalRepository
import javax.inject.Inject

class GetTagListFromLocal @Inject constructor(private val tagLocalRepository: TagLocalRepository) :
    GeneralUseCase<GetTagListFromLocal.RequestValues, DatabaseResult<List<Tag>>>() {

    class RequestValues : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        tagLocalRepository.getTagList()
}