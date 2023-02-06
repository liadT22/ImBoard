package com.example.imboard.repository.FirebaseImpl

import android.location.Location
import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import il.co.syntax.myapplication.util.Resource
import java.net.URI

interface AuthRepository {
    suspend fun currentUser(): Resource<User>
    suspend fun login(email:String, password:String) :Resource<User>
    suspend fun createUser(userName : String, userEmail:String, userLoginPassword: String) : Resource<User>
    fun logout()
}


//val name:String ="",
//val email:String="",
//val phone:String="",
//val location : Location,
//val games: List<Game>,
//val lobbies: List<Lobby>,
//val picture: URI,
//val id: Int