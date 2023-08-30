package com.yerayyas.pagingmarvelapi.presentation.superheroes.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import com.yerayyas.pagingmarvelapi.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest

class SuperheroListViewModel(private val repository: SuperheroesRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val superheroesFlow: Flow<PagingData<SuperheroItemResponse>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getSuperheroesPagingFlow()
            } else {
                repository.getSuperheroesPagingFlow(query)
            }
        }

    val superheroes = superheroesFlow.asLiveData()



    suspend fun setSearchQuery(query: String) {
        _searchQuery.emit(query)
    }


    suspend fun refreshSuperheroes() {
        repository.refreshSuperheroes()
    }
}
