package com.yerayyas.pagingmarvelapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.data.paging.SuperheroesPagingSource
import com.yerayyas.pagingmarvelapi.utils.Constants.API_KEY
import com.yerayyas.pagingmarvelapi.utils.Constants.HASH
import com.yerayyas.pagingmarvelapi.utils.Constants.PAGE_SIZE
import com.yerayyas.pagingmarvelapi.utils.Constants.TS
import kotlinx.coroutines.flow.Flow

class SuperheroesRepository(private val apiService: MarvelApiService) {
    private val superheroesPagingSource = SuperheroesPagingSource(apiService)

    fun getSuperheroesPagingSource(): PagingSource<Int, SuperheroItemResponse> {
        return superheroesPagingSource
    }

    suspend fun refreshSuperheroes() {
        try {
            // Lógica para obtener nuevos datos de la API de Marvel
            val newSuperheroesResponse = apiService.getSuperheroes(API_KEY, HASH, TS, PAGE_SIZE, 0)

            if (newSuperheroesResponse.isSuccessful) {
                val newSuperheroes = newSuperheroesResponse.body()?.data?.superheroes
                newSuperheroes?.let {
                    superheroesPagingSource.updateData(newSuperheroes) // Actualiza los datos en el PagingSource
                    superheroesPagingSource.invalidate() // Invalida para refrescar los datos en Paging
                }
            } else {
                // Manejar error si la respuesta no es exitosa
            }
        } catch (e: Exception) {
            // Manejo de errores
        }
    }

    fun getSuperheroesPagingFlow(searchQuery: String = ""): Flow<PagingData<SuperheroItemResponse>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { SuperheroesPagingSource(apiService, searchQuery) }
        ).flow
    }
}
