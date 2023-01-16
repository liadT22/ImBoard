package com.example.imboard.repository.FirebaseImpl

import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.google.firebase.firestore.FirebaseFirestore
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall

class LobbyRepositoryFirebase : LobbiesRepository{
    private val taskRef = FirebaseFirestore.getInstance().collection("Lobbies")
    override suspend fun addLobby(title: String, game: Game) =
        withContext(Dispatchers.IO){
            safeCall {
                val lobbyId = taskRef.document().id
                val lobby = Lobby(lobbyId, title, game)
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