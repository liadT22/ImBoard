package com.example.imboard.model

import android.location.Location
import com.google.type.DateTime

data class Lobby(val id: String = "",
                 val lobbyName: String = "",
                 val game: Game = Game(),
                 val host: User = User(),
                 val currentPlayers : List<User>? = null,
                 val dateAndTime: DateTime = DateTime.getDefaultInstance(),
                 val location: String = "",
                 val inviteLink: String = "",
                 val haveTheGame : Boolean = false,
                 val hostAtHome : Boolean = false
                 )