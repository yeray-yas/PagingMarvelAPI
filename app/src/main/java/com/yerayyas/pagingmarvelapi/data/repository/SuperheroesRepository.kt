package com.yerayyas.pagingmarvelapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.database.SuperheroDao
import com.yerayyas.pagingmarvelapi.data.database.model.SuperheroEntity
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.paging.SearchSuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.data.paging.SuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.utils.Constants.API_KEY
import com.yerayyas.pagingmarvelapi.utils.Constants.HASH
import com.yerayyas.pagingmarvelapi.utils.Constants.PAGE_SIZE
import com.yerayyas.pagingmarvelapi.utils.Constants.TS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuperheroesRepository @Inject constructor(
    private val apiService: MarvelApiService,
    private val superheroDao: SuperheroDao
) {

    suspend fun refreshSuperheroes() {
        try {
            val response = apiService.getSuperheroes(API_KEY, HASH, TS, PAGE_SIZE, 0)
            if (response.isSuccessful) {
                val superheroes = response.body()?.data?.superheroes ?: emptyList()
                superheroDao.insertAll(superheroes.map { superheroResponse ->
                    SuperheroEntity(
                        superheroResponse.superheroId,
                        superheroResponse.name,
                        superheroResponse.description,
                        "${superheroResponse.thumbnail.path}.${superheroResponse.thumbnail.extension}"
                    )
                })
            }
        } catch (e: Exception) {
            // Manejar errores
        }
    }

    private fun getSuperheroesPagingSource(): PagingSource<Int, SuperheroItemResponse> {
        return SuperheroesPagingSource(apiService)
    }

    fun getSuperheroesPagingFlow(): Flow<PagingData<SuperheroItemResponse>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { getSuperheroesPagingSource() }
        ).flow
    }

    fun getSearchSuperheroesPagingFlow(searchQuery: String): Flow<PagingData<SuperheroItemResponse>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { SearchSuperheroesPagingSource(apiService, searchQuery) }
        ).flow
    }
}

