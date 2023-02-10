package com.example.imboard.ui.all_lobbies

import androidx.lifecycle.*
import com.example.imboard.model.Lobby
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.LobbiesRepository
import il.co.syntax.myapplication.util.Resource

class AllLobbiesViewModel(private val authRep: AuthRepository, val taskRep: LobbiesRepository) :ViewModel()  {

    private val _lobbiesStatus : MutableLiveData<Resource<List<Lobby>>> = MutableLiveData()
    val lobbiesStatus : LiveData<Resource<List<Lobby>>> = _lobbiesStatus

    private val _addLobbyStatus = MutableLiveData<Resource<Void>>()
    val addLobbyStatus: LiveData<Resource<Void>> = _addLobbyStatus

    private val _deleteLobbyStatus = MutableLiveData<Resource<Void>>()
    val deleteLobbyStatus: LiveData<Resource<Void>> = _deleteLobbyStatus
}
