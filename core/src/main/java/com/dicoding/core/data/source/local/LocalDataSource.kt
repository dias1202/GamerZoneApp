package com.dicoding.core.data.source.local

import com.dicoding.core.data.source.local.entity.GameDetailEntity
import com.dicoding.core.data.source.local.entity.GameEntity
import com.dicoding.core.data.source.local.room.GameDao
import com.dicoding.core.domain.model.GameDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val gameDao: GameDao) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getALlGames()

    fun getFavoriteGame(): Flow<List<GameEntity>> = gameDao.getFavoriteGame()

    suspend fun insertGame(gameList: List<GameEntity>) = gameDao.insertGame(gameList)

    suspend fun setFavoriteGame(gameId: Int, newState: Boolean) {
        gameDao.updateFavoriteInBothTables(gameId, newState)
    }

    fun getDetailGame(gameId: Int): Flow<GameDetailEntity> = gameDao.getDetailGame(gameId)

    suspend fun insertGameDetail(game: GameDetailEntity) = gameDao.insertGameDetail(game)

    fun searchGame(query: String): Flow<List<GameEntity>> = gameDao.searchGame(query)

}