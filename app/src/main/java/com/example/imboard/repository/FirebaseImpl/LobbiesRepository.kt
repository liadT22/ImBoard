package com.example.imboard.repository.FirebaseImpl

import androidx.lifecycle.MutableLiveData
import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import il.co.syntax.myapplication.util.Resource

interface LobbiesRepository {
    suspend fun addLobby(lobbyName: String, location : String , host: User, game: Game, date: String, time:String, haveTheGame : Boolean, lobbyPlayers: ArrayList<User>) : Resource<Lobby>
    suspend fun deleteLobby(lobbyId: String) : Resource<Void>
    suspend fun AddNewPlayer(lobbyId: String, lobbyPlayers: ArrayList<User>): Resource<Void>
    suspend fun getLobby(id:String): Resource<Lobby>
    suspend fun getAllLobbies(): Resource<List<Lobby>>
    fun getLobbiesLiveData(data :MutableLiveData<Resource<List<Lobby>>>)
}