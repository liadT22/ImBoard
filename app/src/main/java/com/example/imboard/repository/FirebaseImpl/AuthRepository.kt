package com.example.imboard.repository.FirebaseImpl

import android.net.Uri
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import il.co.syntax.myapplication.util.Resource

interface AuthRepository {
    suspend fun currentUser(): Resource<User>
    suspend fun login(email:String, password:String) :Resource<User>
    suspend fun createUser(
        userName: String,
        userEmail: String,
        userLoginPassword: String,
    ) : Resource<User>
    suspend fun addLobby(userID:String, lobbies: ArrayList<Lobby>):Resource<Void>
    fun logout()
}

