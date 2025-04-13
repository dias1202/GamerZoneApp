package com.dicoding.core.domain.usecase

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Game
import com.dicoding.core.domain.model.GameDetail
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAllGames(): Flow<Resource<List<Game>>>

    fun getFavoriteGame(): Flow<List<Game>>

    suspend fun setFavoriteGame(game: GameDetail, state: Boolean)

    fun getDetailGame(gameId: Int): Flow<Resource<GameDetail>>

    fun searchGame(query: String): Flow<List<Game>>

}