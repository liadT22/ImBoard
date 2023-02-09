package com.example.imboard.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imboard.R
import com.example.imboard.databinding.LobbyRecyclerViewBinding

class LobbyAdapterFix(val lobbys: List<Lobby>) :
    RecyclerView.Adapter<LobbyAdapterFix.LobbyViewHolder>() {

    class LobbyViewHolder(private val binding: LobbyRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lobby: Lobby) {
            binding.lobbyGameImage.setImageResource(lobby.lobby_image)
            binding.lobbyName.text = lobby.lobby_name
            binding.lobbyPlayersCount.text = "Players: ${lobby.max_players} - ${lobby.min_players}"
            binding.lobbyLocation.text = lobby.location
            binding.lobbyDate.text = lobby.date
            binding.lobbyHaveGame.setImageResource(haveGame(lobby))

            }
        fun haveGame(lobby: Lobby): Int {
            if(lobby.have_game)
                return R.drawable.have_game
            else return R.drawable.dont_have
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LobbyViewHolder(LobbyRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) =
        holder.bind(lobbys[position])

    override fun getItemCount() = lobbys.size
}
