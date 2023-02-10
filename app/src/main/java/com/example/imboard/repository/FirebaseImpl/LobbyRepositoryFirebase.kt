package com.example.imboard.repository.FirebaseImpl

import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import com.google.type.DateTime
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall
import java.sql.Time

class LobbyRepositoryFirebase : LobbiesRepository{
    private val taskRef = FirebaseFirestore.getInstance().collection("Lobbies")

    override suspend fun addLobby(
        lobbyName: String,
        lobbyImage: Int,
        location: String,
        host: User,
        game: Game,
        lobbyMaxPlayers: Int,
        date: String,
        haveTheGame: Boolean
    ) = withContext(Dispatchers.IO){
            safeCall {
                val lobbyId = taskRef.document().id
                val lobby = Lobby(lobbyId,host,lobbyImage,lobbyName,game,lobbyMaxPlayers,0,location,date,haveTheGame)
                val addition = taskRef.document(lobbyId).set(lobby).await()
                Resource.Success(addition)
            }
        }

    override suspend fun deleteLobby(lobbyId: String) = withContext(Dispatchers.IO){
        safeCall {
            val result = taskRef.document(lobbyId).delete().await()
            Resource.Success(result)
        }
    }

    override suspend fun setAgeRestriction(lobbyId: String, ageRestriction: Int) = withContext(Dispatchers.IO){
        safeCall {
            val result = taskRef.document(lobbyId).update("age restriction",ageRestriction).await()
            Resource.Success(result)
        }
    }

    override suspend fun getLobby(id: String) = withContext(Dispatchers.IO){
        safeCall {
            val result = taskRef.document(id).get().await()
            val lobby = result.toObject(Lobby::class.java)
            Resource.Success(lobby!!)
        }
    }

    override suspend fun getAllLobbies() = withContext(Dispatchers.IO){
        safeCall {
            val result = taskRef.get().await()
            val tasks = result.toObjects(Lobby::class.java)
            Resource.Success(tasks)
        }
    }
}