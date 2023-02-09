package com.example.imboard.fragments

data class Lobby(
    val lobby_image: Int,
    val lobby_name: String,
    val max_players: Int,
    val min_players: Int,
    val location: String,
    val date: String,
    val have_game:Boolean
)

object LobbyManager{
    val Lobbys : MutableList<Lobby> = mutableListOf()

    fun addLobby(lobby : Lobby){
        Lobbys.add(lobby)
    }

    fun removeLobby(intex:Int){
        Lobbys.removeAt(intex)
    }
}
