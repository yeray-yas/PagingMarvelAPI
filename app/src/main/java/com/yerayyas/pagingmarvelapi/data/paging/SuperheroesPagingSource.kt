package com.yerayyas.pagingmarvelapi.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.utils.Constants.API_KEY
import com.yerayyas.pagingmarvelapi.utils.Constants.HASH
import com.yerayyas.pagingmarvelapi.utils.Constants.TS

class SuperheroesPagingSource(private val apiService: MarvelApiService) : PagingSource<Int, SuperheroItemResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SuperheroItemResponse> {
        try {
            val page = params.key ?: 0
            val limit = params.loadSize // Obtén el tamaño de carga desde los parámetros
            val offset = page * limit // Calcula el offset

            val response = apiService.getSuperheroes(API_KEY, HASH, TS, limit, offset)
            Log.i("PUTAZO", "API Response: ${response.raw()}")

            if (response.isSuccessful) {
                val superheroes = response.body()?.data?.superheroes
                Log.i("PUTAZO", "Superheroes Data: $superheroes")
                return if (superheroes != null) {
                    val prevKey = if (page == 0) null else page - 1
                    val nextKey = if (superheroes.isEmpty()) null else page + 1

                    LoadResult.Page(
                        data = superheroes,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } else {
                    // La respuesta contiene un cuerpo, pero no hay datos de superhéroes
                    LoadResult.Error(Exception("No se encontraron superhéroes"))
                }
            } else {
                // La respuesta no fue exitosa
                return LoadResult.Error(Exception("Respuesta no exitosa: ${response.code()}"))
            }
        } catch (e: Exception) {
            // Manejo de otros errores
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SuperheroItemResponse>): Int? {
        // Intentamos encontrar la página más cercana a la posición anclada (anchorPosition)
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition)

        // Devolvemos la página anterior o siguiente según corresponda
        return closestPage?.prevKey ?: closestPage?.nextKey
    }

    private var currentData: List<SuperheroItemResponse> = emptyList()

    fun updateData(newData: List<SuperheroItemResponse>) {
        currentData = newData
    }

//    companion object {
//        fun create(apiService: MarvelApiService): SuperheroesPagingSource {
//            return SuperheroesPagingSource(apiService, emptyList())
//        }
//    }
}
