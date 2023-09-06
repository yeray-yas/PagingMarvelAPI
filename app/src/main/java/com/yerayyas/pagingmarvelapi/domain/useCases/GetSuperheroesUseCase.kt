package com.yerayyas.pagingmarvelapi.domain.useCases

import androidx.paging.PagingData
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSuperheroesUseCase @Inject constructor(private val repository: SuperheroesRepository) {

    fun execute(): Flow<PagingData<SuperheroItemResponse>> {
        return repository.getSuperheroesPagingFlow()
    }
}