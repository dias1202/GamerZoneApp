package com.dicoding.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding.core.data.source.local.converter.Converter
import com.dicoding.core.data.source.local.entity.GameDetailEntity
import com.dicoding.core.data.source.local.entity.GameEntity

@Database(entities = [GameEntity::class, GameDetailEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converter::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}