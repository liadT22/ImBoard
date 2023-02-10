package com.example.imboard.ui.new_lobby

import androidx.lifecycle.*
import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.LobbiesRepository
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch

class NewLobbyViewModel(private val authRep: AuthRepository, val lobbyRep: LobbiesRepository) :ViewModel() {
    private val _lobbiesStatus : MutableLiveData<Resource<List<Lobby>>> = MutableLiveData()
    val lobbiesStatus: LiveData<Resource<List<Lobby>>> = _lobbiesStatus

    private val _addLobbyStatus = MutableLiveData<Resource<Void>>()
    val addLobbyStatus: LiveData<Resource<Void>> = _addLobbyStatus

    private val _deleteLobbyStatus = MutableLiveData<Resource<Void>>()
    val deleteLobbyStatus: LiveData<Resource<Void>> = _deleteLobbyStatus

    fun addLobby(lobbyName: String, location: String, game: Game, date: String, time:String, haveTheGame: Boolean){
        viewModelScope.launch {
            if(lobbyName.isEmpty() || location.isEmpty() || game == null){
                _addLobbyStatus.postValue(Resource.Error("Empty name, or location"))
            }
            else{
                _addLobbyStatus.postValue(Resource.Loading())
                _addLobbyStatus.postValue(authRep.currentUser().data?.let {
                    lobbyRep.addLobby(lobbyName, location,
                        it, game,date, time, haveTheGame)
                })
            }
        }
    }
    class NewLobbiesViewModelFactory(val authRep: AuthRepository, val lobbyRep: LobbiesRepository) :ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewLobbyViewModel(authRep, lobbyRep) as T
        }
    }
}