package com.example.imboard.ui.all_lobbies

import androidx.lifecycle.*
import com.example.imboard.model.Lobby
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.GameRepository
import com.example.imboard.repository.FirebaseImpl.LobbiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllLobbiesViewModel @Inject constructor(
    lobbyRep: LobbiesRepository,
    gameRepository: GameRepository
    ) :ViewModel()  {

    private val _lobbiesStatus : MutableLiveData<Resource<List<Lobby>>> = MutableLiveData()
    val lobbiesStatus : LiveData<Resource<List<Lobby>>> = _lobbiesStatus
    val games = gameRepository.getGames()

    init {
        lobbyRep.getLobbiesLiveData(_lobbiesStatus)
    }

//    class AllLobbiesViewModelFactory(val lobbyRep: LobbiesRepository):ViewModelProvider.Factory{
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return AllLobbiesViewModel(lobbyRep) as T
//        }
//    }
}