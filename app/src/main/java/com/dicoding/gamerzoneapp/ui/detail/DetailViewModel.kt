package com.dicoding.gamerzoneapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.GameDetail
import com.dicoding.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val gameUseCase: GameUseCase) : ViewModel() {
    private val game = MutableLiveData<GameDetail>()
    val gameDetail: LiveData<GameDetail> get() = game


    fun getGameDetail(gameId: Int): LiveData<Resource<GameDetail>> =
        gameUseCase.getDetailGame(gameId).asLiveData()

    fun setGame(gameDetail: GameDetail) {
        this.game.value = gameDetail
    }

    fun setFavoriteGame(): Boolean {
        val game = game.value
        if (game != null) {
            val newState = !game.isFavorite
            viewModelScope.launch {
                gameUseCase.setFavoriteGame(game, newState)
            }
            this.game.value = game.copy(isFavorite = newState)
            return newState
        }
        return false
    }

}
