package com.example.imboard.ui.all_lobbies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.imboard.R
import com.example.imboard.databinding.LobbyScreenBinding
import com.example.imboard.model.AccountRecycler
import com.example.imboard.model.Lobby
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LobbyScreen : Fragment() {
    private var binding : LobbyScreenBinding by autoCleared()

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
            binding.lobbyGameName.text = lobby.game.name
            binding.lobbyPlayerCount.text = "Players: ${lobby.lobby_players.size}/${lobby.game.max_players}"
            binding.lobbyName.text = lobby.lobby_name
            Glide.with(binding.root).load(lobby.game.image_url).into(binding.lobbyGameIcon)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lobby: Lobby? = arguments?.getParcelable<Lobby>("lobby")
        super.onViewCreated(view, savedInstanceState)
        binding.accountRecycler.adapter =
            lobby?.let {
                AccountRecyclerAdapter(AccountRecycler.ItemManager.items,
                    it.lobby_players)
            }
        binding.accountRecycler.layoutManager = lobby?.lobby_players?.size?.let {
            GridLayoutManager(requireContext(), it) }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}