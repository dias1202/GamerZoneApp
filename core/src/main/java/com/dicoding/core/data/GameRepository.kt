package com.dicoding.core.data

import android.util.Log
import com.dicoding.core.data.source.local.LocalDataSource
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.response.GameDetailResponse
import com.dicoding.core.data.source.remote.response.ResultsItem
import com.dicoding.core.domain.model.Game
import com.dicoding.core.domain.model.GameDetail
import com.dicoding.core.domain.repository.IGameRepository
import com.dicoding.core.utils.AppExecutors
import com.dicoding.core.utils.DataMapper.toDomain
import com.dicoding.core.utils.DataMapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {

    override fun getAllGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Game>> =
                localDataSource.getAllGames().map { list -> list.map { it.toDomain() } }

            override fun shouldFetch(data: List<Game>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllGames()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                localDataSource.insertGame(data.map { it.toEntity() })
            }
        }.asFlow()

    override fun getFavoriteGame(): Flow<List<Game>> =
        localDataSource.getFavoriteGame().map { list -> list.map { it.toDomain() } }

    override suspend fun setFavoriteGame(game: GameDetail, state: Boolean) {
        localDataSource.setFavoriteGame(game.id, state)
    }

    override fun getDetailGame(gameId: Int): Flow<Resource<GameDetail>> =
        object : NetworkBoundResource<GameDetail, GameDetailResponse>() {
            override fun loadFromDB(): Flow<GameDetail> {
                return localDataSource.getDetailGame(gameId)
                    .map { entity -> entity.toDomain() }
                    .catch {
                        emit(
                            GameDetail(
                                0,
                                "Unknown",
                                "No description",
                                "Unknown",
                                0.0,
                                "",
                                listOf(""),
                                false
                            )
                        )
                    }
            }

            override fun shouldFetch(data: GameDetail?): Boolean {
                val isNull =
                    data == null || data.name == "Unknown"
                val shouldFetch = isNull || data?.description?.isEmpty() == true
                return shouldFetch
            }

            override suspend fun createCall(): Flow<ApiResponse<GameDetailResponse>> =
                remoteDataSource.getDetailGame(gameId)

            override suspend fun saveCallResult(data: GameDetailResponse) {
                val gameEntity = data.toEntity()
                if (gameEntity != null) {
                    localDataSource.insertGameDetail(gameEntity)
                } else {
                    Log.e("GameRepository", "Invalid data from API: $data")
                }
            }

        }.asFlow()

    override fun searchGame(query: String): Flow<List<Game>> =
        localDataSource.searchGame(query).map { list -> list.map { it.toDomain() } }
}
