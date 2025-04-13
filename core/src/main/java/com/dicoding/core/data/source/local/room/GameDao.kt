package com.dicoding.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import com.dicoding.core.data.source.local.converter.Converter
import com.dicoding.core.data.source.local.entity.GameDetailEntity
import com.dicoding.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getALlGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE is_favorite = 1")
    fun getFavoriteGame(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: List<GameEntity>)

    @Query("UPDATE game SET is_favorite = :state WHERE id = :id")
    suspend fun setGameFavoriteState(id: Int, state: Boolean)

    @Query("UPDATE game_detail SET is_favorite = :state WHERE id = :id")
    suspend fun setGameDetailFavoriteState(id: Int, state: Boolean)

    @Transaction
    suspend fun updateFavoriteInBothTables(id: Int, state: Boolean) {
        setGameFavoriteState(id, state)
        setGameDetailFavoriteState(id, state)
    }

    @Query("SELECT * FROM game_detail WHERE id = :gameId")
    fun getDetailGame(gameId: Int): Flow<GameDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameDetail(game: GameDetailEntity)

    @Query("SELECT * FROM game WHERE name LIKE '%' || :query || '%'")
    fun searchGame(query: String): Flow<List<GameEntity>>

}