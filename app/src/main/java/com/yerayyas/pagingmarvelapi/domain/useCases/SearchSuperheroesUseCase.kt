package com.yerayyas.pagingmarvelapi.domain.useCases

import androidx.paging.PagingData
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import kotlinx.coroutines.flow.Flow

class SearchSuperheroesUseCase(private val repository: SuperheroesRepository) {

    fun execute(query: String): Flow<PagingData<SuperheroItemResponse>> {
        return repository.getSearchSuperheroesPagingFlow(query)
    }
}