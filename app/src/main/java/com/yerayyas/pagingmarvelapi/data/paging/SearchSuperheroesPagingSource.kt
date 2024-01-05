package com.yerayyas.pagingmarvelapi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.utils.Constants.API_KEY
import com.yerayyas.pagingmarvelapi.utils.Constants.HASH
import com.yerayyas.pagingmarvelapi.utils.Constants.TS
import java.io.IOException

class SearchSuperheroesPagingSource(
    private val apiService: MarvelApiService,
    private val searchQuery: String
) : PagingSource<Int, SuperheroItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SuperheroItemResponse> {
        try {
            val page = params.key ?: 0
            val limit = params.loadSize
            val offset = page * limit

            val response = apiService.getSuperheroByName(
                nameStartsWith = searchQuery,
                apiKey = API_KEY,
                hash = HASH,
                ts = TS,
                limit = limit,
                offset = offset
            )

            if (response.isSuccessful) {
                val superheroes = response.body()?.data?.superheroes
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
                val errorMessage = when (response.code()) {
                    401 -> "Error de autenticación: API key inválida"
                    404 -> "No se encontraron superhéroes para la búsqueda '$searchQuery'"
                    in 500 until 600 -> "Error del servidor: ${response.code()}"
                    else -> "Error desconocido: ${response.code()}"
                }
                return LoadResult.Error(Exception(errorMessage))
            }
        } catch (e: IOException) {
            // Error de conexión a Internet
            return LoadResult.Error(Exception("Error de conexión a Internet"))
        } catch (e: Exception) {
            // Otros errores
            return LoadResult.Error(Exception("Error cargando datos: ${e.message}", e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SuperheroItemResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition)
        return closestPage?.prevKey ?: closestPage?.nextKey
    }
}

