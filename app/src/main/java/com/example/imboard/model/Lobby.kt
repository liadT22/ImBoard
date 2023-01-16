package com.example.imboard.model

import android.location.Location
import com.google.type.DateTime

data class Lobby(val host: User,
                 val currentPlayers : List<User>,
                 val game: Game,
                 val dateAndTime: DateTime,
                 val location: Location,
                 val inviteLink: String,
                 val ageRestriction: Int,
                 val lobbyName: String,
                 val id: Int)