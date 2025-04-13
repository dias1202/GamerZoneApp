package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.model.Game
import com.dicoding.core.domain.model.GameDetail
import com.dicoding.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gameRepository: IGameRepository): GameUseCase {
    override fun getAllGames() = gameRepository.getAllGames()

    override fun getFavoriteGame() = gameRepository.getFavoriteGame()

    override suspend fun setFavoriteGame(game: GameDetail, state: Boolean) = gameRepository.setFavoriteGame(game, state)

    override fun getDetailGame(gameId: Int) = gameRepository.getDetailGame(gameId)

    override fun searchGame(query: String): Flow<List<Game>> = gameRepository.searchGame(query)
}