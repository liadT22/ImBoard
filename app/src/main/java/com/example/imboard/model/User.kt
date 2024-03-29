package com.example.imboard.model

import android.location.Location
import android.net.Uri
import android.os.Parcelable
import com.google.api.Context
import kotlinx.parcelize.Parcelize
import java.net.URI

@Parcelize
data class User(val name:String ="",
                val email:String="",
                val id: String = "",
                val uri: String = "",
                val games: ArrayList<Game>? = null,
                val lobbies: ArrayList<Lobby>? = ArrayList()) : Parcelable
