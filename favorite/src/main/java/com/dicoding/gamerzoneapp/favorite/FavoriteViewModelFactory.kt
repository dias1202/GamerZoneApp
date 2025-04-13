package com.dicoding.gamerzoneapp.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.core.di.GameUseCaseEntryPoint
import dagger.hilt.android.EntryPointAccessors

class FavoriteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            GameUseCaseEntryPoint::class.java
        )
        return FavoriteViewModel(entryPoint.gameUseCase()) as T
    }
}