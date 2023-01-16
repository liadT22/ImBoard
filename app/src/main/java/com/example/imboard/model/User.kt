package com.example.imboard.model

import android.location.Location
import com.google.api.Context
import java.net.URI

data class User(val name:String ="",
                val email:String="",
                val phone:String="",
                val location : Location,
                val games: List<Game>,
                val lobbies: List<Lobby>,
                val picture: URI,
                val id: Int)