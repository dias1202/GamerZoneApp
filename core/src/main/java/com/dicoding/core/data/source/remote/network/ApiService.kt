package com.dicoding.core.data.source.remote.network

import com.dicoding.core.BuildConfig.API_KEY
import com.dicoding.core.data.source.remote.response.GameDetailResponse
import com.dicoding.core.data.source.remote.response.GameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String = API_KEY
    ): GameResponse

    @GET("games/{gameId}")
    suspend fun getDetailGame(
        @Path("gameId") gameId: Int,
        @Query("key") apiKey: String = API_KEY
    ): GameDetailResponse
}