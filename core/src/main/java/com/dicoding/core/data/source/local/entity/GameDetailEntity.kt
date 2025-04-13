package com.dicoding.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_detail")
data class GameDetailEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "released")
    val released: String?,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String?,

    @ColumnInfo(name = "platforms")
    val platforms: List<String?>,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)