package com.example.imboard.ui.all_lobbies

import AccountAdapterInLobby
import android.accounts.AccountManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.imboard.R
import com.example.imboard.databinding.LobbyScreenBinding
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.ui.recycleViewAccounts.UserManager.accounts
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LobbyScreen : Fragment() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var binding : LobbyScreenBinding by autoCleared()
    //private lateinit var currentUser : Resource<User>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LobbyScreenBinding.inflate(inflater, container, false)
        val lobby: Lobby? = arguments?.getParcelable<Lobby>("lobby")
        if(lobby != null){
            binding.lobbyDateLobbyRoom.text = "${lobby.lobby_date} - ${lobby.lobby_time}"
            binding.lobbyLocationLobbyRoom.text = lobby.lobby_location
            binding.lobbyGameName.text = "lobby.game.name"
            binding.lobbyPlayerCount.text = "Players: ${lobby.lobby_players.size}/${lobby.game.max_players}"
            binding.lobbyName.text = lobby.lobby_name
        }
        Glide.with(binding.root).load(R.drawable.have_game).circleCrop().into(binding.lobbyGameIcon)

        coroutineScope.launch {
        }

        if (lobby != null) {
            val playersCount = lobby.lobby_players.size

            binding.recycler.adapter = AccountAdapterInLobby(lobby.lobby_players)
            binding.recycler.layoutManager = GridLayoutManager(requireContext(),playersCount)

            binding.lobbyJoinBtn.setOnClickListener{
                if(lobby.game.max_players<= playersCount) //to check if
                {
                  //lobby.lobby_players[playersCount] = lobby.lobby_players[0]// need to change to currentUser().data
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}