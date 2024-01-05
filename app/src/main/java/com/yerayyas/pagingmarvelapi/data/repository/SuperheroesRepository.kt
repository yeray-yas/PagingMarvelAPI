package com.yerayyas.pagingmarvelapi.data.repository

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.paging.SearchSuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.data.paging.SuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope

class SuperheroesRepository @Inject constructor(private val apiService: MarvelApiService):ViewModel() {

    private fun getSuperheroesPagingSource(): PagingSource<Int, SuperheroItemResponse> {
        return SuperheroesPagingSource(apiService)
    }

    fun getSuperheroesPagingFlow(): Flow<PagingData<SuperheroItemResponse>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { getSuperheroesPagingSource() }
        ).flow.cachedIn(viewModelScope)
    }

    fun getSearchSuperheroesPagingFlow(searchQuery: String): Flow<PagingData<SuperheroItemResponse>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { SearchSuperheroesPagingSource(apiService, searchQuery) }
        ).flow.cachedIn(viewModelScope)
    }
}

