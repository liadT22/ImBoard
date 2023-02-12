package com.example.imboard.ui.lobby_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.LobbiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyScreenViewModel @Inject constructor(authRep: AuthRepository,private val lobbyRepository: LobbiesRepository): ViewModel() {
    private val _currentLobby = MutableLiveData<Resource<Lobby>>()
    val currentLobby : LiveData<Resource<Lobby>> = _currentLobby

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>> = _currentUser

    init {
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading())
            if (authRep.currentUser().status.data == null) {
                _currentUser.postValue(Resource.error("GuestMode On"))
            } else {
                _currentUser.postValue(authRep.currentUser())
            }
        }
    }
    fun setLobby(lobby: Lobby?){
        viewModelScope.launch {
            _currentLobby.postValue(Resource.loading())
            if(lobby == null){
                _currentLobby.postValue(Resource.error("cant find lobby"))
            }else{
                val result = lobbyRepository.setLobbyPlayers(lobby.lobby_id!!,lobby.lobby_players!!)
                _currentLobby.postValue(Resource.success(lobby))
            }
        }
    }
}