package com.dicoding.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.core.data.source.local.room.GameDao
import com.dicoding.core.data.source.local.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    val passphrase: ByteArray = SQLiteDatabase.getBytes("dias".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase = Room.databaseBuilder(
        context,
        GameDatabase::class.java, "Game.db"
    )
        .fallbackToDestructiveMigration(dropAllTables = true)
        .openHelperFactory(factory)
        .build()

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()
}