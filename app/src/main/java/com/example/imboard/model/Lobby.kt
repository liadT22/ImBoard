package com.example.imboard.model

import android.location.Location
import android.os.Parcelable
import com.google.type.DateTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Serializable

@Parcelize
data class Lobby(val lobby_id:String? = "",
                 val host :User = User(),
                 val lobby_name: String? = "",
                 val game: Game = Game(),
                 val lobby_location: String? = "",
                 val lobby_date: String? = "",
                 val lobby_time: String? = "",
                 val lobby_have_game: Boolean? = false,
                 val lobby_players: ArrayList<User> = ArrayList()
) : Parcelable