package com.dicoding.core.di

import com.dicoding.core.domain.usecase.GameInteractor
import com.dicoding.core.domain.usecase.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GameModule {

    @Binds
    abstract fun bindGameUseCase(gameInteractor: GameInteractor): GameUseCase
}