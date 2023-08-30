package com.yerayyas.pagingmarvelapi.presentation.superheroes.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SuperheroListViewModel(private val repository: SuperheroesRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    //val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchSuperheroes: Flow<PagingData<SuperheroItemResponse>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getSuperheroesPagingFlow()
            } else {
                repository.getSearchSuperheroesPagingFlow(query)
            }
        }
        .cachedIn(viewModelScope)

    val superheroes: Flow<PagingData<SuperheroItemResponse>> = searchSuperheroes

    suspend fun setSearchQuery(query: String) {
        _searchQuery.emit(query)
    }

//    suspend fun refreshSuperheroes() {
//        repository.refreshSuperheroes()
//    }
}


