package com.example.imboard.ui.new_lobby

import androidx.lifecycle.*
import com.example.imboard.R
import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.GameRepository
import com.example.imboard.repository.FirebaseImpl.LobbiesRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewLobbyViewModel @Inject constructor(private val authRep: AuthRepository, private val lobbyRep: LobbiesRepository,gameRepository: GameRepository) :ViewModel() {
    private val _lobbiesStatus : MutableLiveData<Resource<List<Lobby>>> = MutableLiveData()
    val lobbiesStatus: LiveData<Resource<List<Lobby>>> = _lobbiesStatus

    private val _addLobbyStatus = MutableLiveData<Resource<Void>>()
    val addLobbyStatus: LiveData<Resource<Void>> = _addLobbyStatus

    val games = gameRepository.getGames()

    fun addLobby(lobbyName: String, location: String, game: Game?, date: String, time:String, haveTheGame: Boolean){
        viewModelScope.launch {
            if(lobbyName.isEmpty() || location.isEmpty() || game == null || date.isEmpty() ||time.isEmpty()){
                _addLobbyStatus.postValue(Resource.error("Please fill the entire form"))
            }
            else{
                val lobbyPlayers : ArrayList<User> = ArrayList()

                _addLobbyStatus.postValue(Resource.loading())
                _addLobbyStatus.postValue(authRep.currentUser().status.data?.let {
                    lobbyPlayers.add(authRep.currentUser().status.data!!)
                    val lobby :Resource<Lobby> = lobbyRep.addLobby(lobbyName, location,
                        it, game,date, time, haveTheGame, lobbyPlayers)
                    lobby.status.data?.let { it1 ->
                        authRep.currentUser().status.data?.lobbies?.add(
                            it1
                        )
                    }
                    val lobbies = authRep.currentUser().status.data!!.lobbies
                    lobby.status.data?.let { it1 -> lobbies?.add(it1) }
                    authRep.addLobby(authRep.currentUser().status.data!!.id,lobbies!!)
                })

            }
        }
    }
}