package com.yerayyas.pagingmarvelapi.data.api

import com.yerayyas.pagingmarvelapi.data.model.ImageDatasResult
import com.yerayyas.pagingmarvelapi.data.model.SuperheroDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelApiService {

    @GET("/v1/public/characters")
    suspend fun getSuperheroByName(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long
    ): Response<SuperheroDataResponse>

    @GET("/v1/public/characters")
    suspend fun getSuperheroes(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<SuperheroDataResponse>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getSuperheroesDetail(
        @Path("characterId") characterId: Int,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long
    ): Response<ImageDatasResult>
}