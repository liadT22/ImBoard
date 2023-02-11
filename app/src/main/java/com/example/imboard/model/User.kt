package com.example.imboard.model

import android.location.Location
import android.net.Uri
import com.google.api.Context
import java.net.URI

data class User(val name:String ="",
                val email:String="",
                val uri: Uri? = null,
                val games: List<Game>? = null,
                val lobbies: List<Lobby>? = null,
                val id: String = ""
)
