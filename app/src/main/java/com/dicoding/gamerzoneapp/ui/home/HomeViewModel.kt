package com.dicoding.gamerzoneapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Game
import com.dicoding.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(gameUseCase: GameUseCase) : ViewModel() {
    val games = gameUseCase.getAllGames().asLiveData()

    private val _filteredGames = MutableLiveData<List<Game>>()
    val filteredGames: LiveData<List<Game>> get() = _filteredGames

    fun searchGame(query: String) {
        val gameList = (games.value as? Resource.Success)?.data.orEmpty()

        _filteredGames.value = if (query.isBlank()) {
            gameList
        } else {
            gameList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

}