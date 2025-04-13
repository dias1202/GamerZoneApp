package com.dicoding.core.di

import com.dicoding.core.domain.usecase.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface GameUseCaseEntryPoint {
    fun gameUseCase(): GameUseCase
}