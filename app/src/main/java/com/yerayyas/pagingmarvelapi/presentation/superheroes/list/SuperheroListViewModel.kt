package com.yerayyas.pagingmarvelapi.presentation.superheroes.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yerayyas.pagingmarvelapi.data.database.SuperheroDao
import com.yerayyas.pagingmarvelapi.data.model.Thumbnail
import com.yerayyas.pagingmarvelapi.domain.useCases.GetSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.domain.useCases.SearchSuperheroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SuperheroListViewModel @Inject constructor(
    private val getSuperheroesUseCase: GetSuperheroesUseCase,
    private val searchSuperheroesUseCase: SearchSuperheroesUseCase,
    private val superheroDao: SuperheroDao
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchSuperheroes: Flow<PagingData<SuperheroItemResponse>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                getSuperheroesUseCase.execute()
            } else {
                searchSuperheroesUseCase.execute(query)
            }
        }
        .cachedIn(viewModelScope)

    val superheroes: Flow<PagingData<SuperheroItemResponse>> = searchSuperheroes

    val offlineSuperheroes: Flow<PagingData<SuperheroItemResponse>> = superheroDao.getAllSuperheroes()
        .map { superheroes ->
            PagingData.from(superheroes.map { superheroEntity ->
                SuperheroItemResponse(
                    superheroEntity.name,
                    superheroEntity.superheroId,
                    superheroEntity.description,
                    Thumbnail(superheroEntity.imageUrl, "jpg")
                )
            })
        }

    suspend fun setSearchQuery(query: String) {
        _searchQuery.emit(query)
    }
}
