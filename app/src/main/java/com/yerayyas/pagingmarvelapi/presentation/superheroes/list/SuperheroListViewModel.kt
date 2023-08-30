package com.yerayyas.pagingmarvelapi.presentation.superheroes.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.paging.SuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import com.yerayyas.pagingmarvelapi.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class SuperheroListViewModel(private val repository: SuperheroesRepository) : ViewModel() {



    val superheroes: Flow<PagingData<SuperheroItemResponse>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { repository.getSuperheroesPagingSource() }
    ).flow


    suspend fun refreshSuperheroes() {
        repository.refreshSuperheroes()
    }
}
