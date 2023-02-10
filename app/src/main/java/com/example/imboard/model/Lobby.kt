package com.example.imboard.model

import android.location.Location
import com.google.type.DateTime

data class Lobby(val lobby_id:String? = "",
                 val host :User = User(),
                 val lobby_image: Int? = 0,
                 val lobby_name: String? = "",
                 val game: Game = Game(),
                 val lobby_max_players: Int? = 0,
                 val lobby_current_players: Int? = 0,
                 val lobby_location: String? = "",
                 val lobby_date: String? = "",
                 val lobby_time: String? = "",
                 val lobby_have_game: Boolean? = false)