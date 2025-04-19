package com.dicoding.core.utils

import android.util.Log
import com.dicoding.core.data.source.local.entity.GameDetailEntity
import com.dicoding.core.data.source.local.entity.GameEntity
import com.dicoding.core.data.source.remote.response.GameDetailResponse
import com.dicoding.core.data.source.remote.response.ResultsItem
import com.dicoding.core.domain.model.Game
import com.dicoding.core.domain.model.GameDetail

object DataMapper {

    fun ResultsItem.toEntity(): GameEntity =
        GameEntity(
            id = this.id,
            name = this.name,
            released = this.released,
            rating = this.rating,
            backgroundImage = this.backgroundImage,
            platforms = this.platforms.toString(),
            isFavorite = false
        )

    fun GameEntity?.toDomain(): Game =
        this?.let {
            Game(
                id = it.id,
                name = it.name,
                released = it.released ?: "Unknown",
                rating = it.rating,
                backgroundImage = it.backgroundImage ?: "",
                platforms = it.platforms,
                isFavorite = it.isFavorite
            )
        } ?: Game(0, "Unknown", "Unknown", 0.0, "", "", false)

    fun Game.toEntity(): GameEntity =
        GameEntity(
            id = this.id,
            name = this.name,
            released = this.released,
            rating = this.rating,
            backgroundImage = this.backgroundImage,
            platforms = this.platforms,
            isFavorite = this.isFavorite
        )

    fun GameDetailResponse.toEntity(): GameDetailEntity? {
        try {
            if (this.platforms == null) {
                Log.e("DataMapper", "Invalid data from API: platforms is null -> $this")
                return null
            }


            val platformNames = this.platforms.mapNotNull { it?.platform?.name }

            val entity = GameDetailEntity(
                id = this.id,
                name = this.name ?: "Unknown",
                description = this.description ?: "No description",
                released = this.released ?: "Unknown",
                rating = this.rating,
                backgroundImage = this.backgroundImage ?: "",
                platforms = platformNames,
                isFavorite = false
            )

            return entity
        } catch (e: Exception) {
            Log.e("DataMapper", "Exception saat mapping: ${e.message}", e)
            return null
        }
    }

    fun GameDetailEntity?.toDomain(): GameDetail =
        this?.let {
            GameDetail(
                id = it.id,
                name = it.name ?: "",
                description = it.description ?: "No description",
                released = it.released ?: "Unknown",
                rating = it.rating,
                backgroundImage = it.backgroundImage ?: "",
                platforms = it.platforms,
                isFavorite = it.isFavorite
            )
        } ?: run {
            GameDetail(0, "Unknown", "No description", "Unknown", 0.0, "", listOf(""), false)
        }

    fun GameDetail.toEntity(): GameDetailEntity =
        GameDetailEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            released = this.released,
            rating = this.rating,
            backgroundImage = this.backgroundImage,
            platforms = this.platforms,
            isFavorite = this.isFavorite
        )

    fun GameEntity.toGameDetail(): GameDetail =
        GameDetail(
            id = this.id,
            name = this.name,
            description = "",
            released = this.released ?: "Unknown",
            rating = this.rating,
            backgroundImage = this.backgroundImage ?: "",
            platforms = this.platforms.split(",").map { it.trim() },
            isFavorite = this.isFavorite
        )

}
