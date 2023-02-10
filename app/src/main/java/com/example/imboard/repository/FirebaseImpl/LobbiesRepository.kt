package com.example.imboard.repository.FirebaseImpl

import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.google.type.DateTime
import il.co.syntax.myapplication.util.Resource

interface LobbiesRepository {
    suspend fun addLobby(lobbyName: String, lobbyImage :Int, location : String , host: User, game: Game ,lobbyMaxPlayers : Int, date: String, haveTheGame : Boolean) : Resource<Void>
    suspend fun deleteLobby(lobbyId: String) : Resource<Void>
    suspend fun setAgeRestriction(lobbyId: String, ageRestriction: Int): Resource<Void>
    suspend fun getLobby(id:String): Resource<Lobby>
    suspend fun getAllLobbies(): Resource<List<Lobby>>
}