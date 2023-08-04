package com.nebka.cryptoeye.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebka.cryptoeye.data.db.DatabaseResult
import com.nebka.cryptoeye.data.models.Tag
import com.nebka.cryptoeye.data.remote.NetworkResult
import com.nebka.cryptoeye.usecases.AddTagList
import com.nebka.cryptoeye.usecases.GetTagListFromLocal
import com.nebka.cryptoeye.usecases.GetTagListFromRemote
import com.nebka.cryptoeye.usecases.GetUpdateTime
import com.nebka.cryptoeye.util.TimeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val addTagList: AddTagList,
    private val getTagListFromLocal: GetTagListFromLocal,
    private val getTagListFromRemote: GetTagListFromRemote,
    private val getUpdateTime: GetUpdateTime,
    private val timeDataStore: TimeDataStore
) : ViewModel() {

    private val _tagListState = MutableStateFlow<List<Tag>>(emptyList())
    val tagListState = _tagListState.asStateFlow()

    private val _errorState = MutableStateFlow("")
    val errorState = _errorState.asStateFlow()

    init {
        getRemoteUpdateTime()
    }

    private suspend inline fun getLocalUpdateTime(serverTime: Long) {
        timeDataStore.getLastUpdateTime.collect { localTime ->
            if (serverTime <= localTime)
                getTagListFromLocal()
            else getTagListFromRemote()
        }
    }

    private fun addTagList(tagList: List<Tag>) {
        viewModelScope.launch {
            addTagList.executeUseCase(AddTagList.RequestValues(tagList)).also {
                when (it) {
                    is DatabaseResult.Success -> {
                        timeDataStore.saveLastUpdateTime(System.currentTimeMillis())
                        getTagListFromLocal()
                    }

                    is DatabaseResult.Error -> {
                        getTagListFromLocal()
                        _errorState.update { _ -> "Database Error: ${it.message}" }
                    }
                }
            }
        }
    }

    private fun getTagListFromLocal() {
        viewModelScope.launch {
            getTagListFromLocal.executeUseCase(GetTagListFromLocal.RequestValues()).also {
                when (it) {
                    is DatabaseResult.Success -> _tagListState.update { _ -> it.data }
                    is DatabaseResult.Error -> _errorState.update { _ -> "Database Error: ${it.message}" }
                }
            }
        }
    }

    private fun getTagListFromRemote() {
        viewModelScope.launch {
            getTagListFromRemote.executeUseCase(GetTagListFromRemote.RequestValues()).also {
                when (it) {
                    is NetworkResult.Success -> {
                        _tagListState.update { _ -> it.data }
                        addTagList(it.data)
                    }

                    is NetworkResult.Error -> {
                        getTagListFromLocal()
                        _errorState.update { _ -> "Network Error: Code ${it.code}, ${it.message}" }
                    }

                    is NetworkResult.Exception -> {
                        getTagListFromLocal()
                        _errorState.update { _ -> "Network Exception: ${it.e.message}" }
                    }
                }
            }
        }
    }

    private fun getRemoteUpdateTime() {
        viewModelScope.launch {
            getUpdateTime.executeUseCase(GetUpdateTime.RequestValues()).also {
                when (it) {
                    is NetworkResult.Success -> getLocalUpdateTime(it.data)
                    is NetworkResult.Error -> {
                        getTagListFromLocal()
                        _errorState.update { _ -> "Network Error: Code ${it.code}, ${it.message}" }
                    }

                    is NetworkResult.Exception -> {
                        getTagListFromLocal()
                        _errorState.update { _ -> "Network Exception: ${it.e.message}" }
                    }
                }
            }
        }
    }
}