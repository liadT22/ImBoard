package com.example.imboard.repository.FirebaseImpl

import androidx.lifecycle.MutableLiveData
import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.google.firebase.firestore.FirebaseFirestore
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall

class LobbyRepositoryFirebase : LobbiesRepository{
    private val lobbyRef = FirebaseFirestore.getInstance().collection("Lobbies")

    override suspend fun addLobby(
        lobbyName: String,
        location: String,
        host: User,
        game: Game,
        date: String,
        time :String,
        haveTheGame: Boolean,
        lobbyPlayers: ArrayList<User>
    ) = withContext(Dispatchers.IO){
            safeCall {
                val lobbyId = lobbyRef.document().id
                val lobby = Lobby(lobbyId,host,lobbyName,game,location,date, time, haveTheGame, lobbyPlayers)
                val addition = lobbyRef.document(lobbyId).set(lobby).await()
                Resource.Success(lobby)
            }
        }

    override suspend fun deleteLobby(lobbyId: String) = withContext(Dispatchers.IO){
        safeCall {
            val result = lobbyRef.document(lobbyId).delete().await()
            Resource.Success(result)
        }
    }

    override suspend fun AddNewPlayer(lobbyId: String, lobbyPlayers: ArrayList<User>) = withContext(Dispatchers.IO) {
        safeCall {
            val result = lobbyRef.document(lobbyId).update("lobby_players", lobbyPlayers).await()
            Resource.Success(result)
        }
    }

    override suspend fun getLobby(id: String) = withContext(Dispatchers.IO){
        safeCall {
            val result = lobbyRef.document(id).get().await()
            val lobby = result.toObject(Lobby::class.java)
            Resource.Success(lobby!!)
        }
    }

    override suspend fun getAllLobbies() = withContext(Dispatchers.IO){
        safeCall {
            val result = lobbyRef.get().await()
            val tasks = result.toObjects(Lobby::class.java)
            Resource.Success(tasks)
        }
    }

    override fun getLobbiesLiveData(data: MutableLiveData<Resource<List<Lobby>>>) {
        data.postValue(Resource.Loading())

        lobbyRef.orderBy("lobby_date").addSnapshotListener{ snapshot, e->
            if(e != null){
                data.postValue(Resource.Error(e.localizedMessage))
            }
            if(snapshot != null && !snapshot.isEmpty){
                data.postValue(Resource.Success(snapshot.toObjects(Lobby::class.java)))
            }
            else{
                data.postValue(Resource.Error("No Data"))
            }
        }
    }
}