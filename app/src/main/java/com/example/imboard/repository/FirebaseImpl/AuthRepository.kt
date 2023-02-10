package com.example.imboard.repository.FirebaseImpl

import android.net.Uri
import com.example.imboard.model.User
import il.co.syntax.myapplication.util.Resource

interface AuthRepository {
    suspend fun currentUser(): Resource<User>
    suspend fun login(email:String, password:String) :Resource<User>
    suspend fun createUser(
        userName: String,
        userEmail: String,
        userLoginPassword: String,
        imageUri: Uri?
    ) : Resource<User>
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