package com.dicoding.core.domain.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Game
import com.dicoding.core.domain.model.GameDetail
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getAllGames(): Flow<Resource<List<Game>>>

    fun getFavoriteGame(): Flow<List<Game>>

    suspend fun setFavoriteGame(game: GameDetail, state: Boolean)

    fun getDetailGame(gameId: Int): Flow<Resource<GameDetail>>

    fun searchGame(query: String): Flow<List<Game>>

}