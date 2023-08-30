package com.yerayyas.pagingmarvelapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.paging.SearchSuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.data.paging.SuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class SuperheroesRepository(private val apiService: MarvelApiService) {

    //private lateinit var superheroesPagingSource: SuperheroesPagingSource

    private fun getSuperheroesPagingSource(): PagingSource<Int, SuperheroItemResponse> {
        return SuperheroesPagingSource(apiService)
    }

//    suspend fun refreshSuperheroes() {
//        try {
//            // Logic to obtain new data from Marvel's API
//            val newSuperheroesResponse = apiService.getSuperheroes(API_KEY, HASH, TS, PAGE_SIZE, 0)
//
//            if (newSuperheroesResponse.isSuccessful) {
//                val newSuperheroes = newSuperheroesResponse.body()?.data?.superheroes
//                newSuperheroes?.let {
//                    superheroesPagingSource.updateData(newSuperheroes)
//                    superheroesPagingSource.invalidate()
//                } ?: throw Exception("No superheroes data found in the response")
//            } else {
//                throw Exception("Unsuccessful response: ${newSuperheroesResponse.code()}")
//            }
//        } catch (e: Exception) {
//            throw Exception("Error refreshing superheroes: ${e.message}", e)
//        }
//    }

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

