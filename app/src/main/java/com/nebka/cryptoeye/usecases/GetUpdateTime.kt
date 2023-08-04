package com.nebka.cryptoeye.usecases

import com.nebka.cryptoeye.data.remote.NetworkResult
import com.nebka.cryptoeye.repos.TagRemoteRepository
import javax.inject.Inject

class GetUpdateTime @Inject constructor(private val tagRemoteRepository: TagRemoteRepository) :
    GeneralUseCase<GetUpdateTime.RequestValues, NetworkResult<Long>>() {

    class RequestValues : GeneralUseCase.RequestValues

    override suspend fun executeUseCase(requestValues: RequestValues) =
        tagRemoteRepository.getUpdateTime()
}