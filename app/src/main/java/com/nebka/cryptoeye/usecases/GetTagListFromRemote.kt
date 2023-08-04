package com.nebka.cryptoeye.usecases

import com.nebka.cryptoeye.data.models.Tag
import com.nebka.cryptoeye.data.remote.NetworkResult
import com.nebka.cryptoeye.repos.TagRemoteRepository
import javax.inject.Inject

class GetTagListFromRemote @Inject constructor(private val tagRemoteRepository: TagRemoteRepository) :
    GeneralUseCase<GetTagListFromRemote.RequestValues, NetworkResult<List<Tag>>>() {

    class RequestValues : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        tagRemoteRepository.getTagList()
}