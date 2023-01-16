package com.example.imboard.model

import android.location.Location
import com.google.type.DateTime

data class Lobby(val id: String = "",
                 val lobbyName: String = "",
                 val game: Game = Game(),
                 val host: User = User(),
                 val currentPlayers : List<User>? = null,
                 val dateAndTime: DateTime = DateTime.getDefaultInstance(),
                 val location: Location? = null,
                 val inviteLink: String = "",
                 val ageRestriction: Int = 0,
                 )