package com.example.imboard.ui.all_lobbies

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imboard.MyApplication
import com.example.imboard.R
import com.example.imboard.databinding.LobbyRecyclerViewBinding
import com.example.imboard.model.Lobby
import com.google.android.material.internal.ContextUtils.getActivity
import java.security.AccessController.getContext

class AllLobbyAdapter(val callBack: LobbyListener) :
    RecyclerView.Adapter<AllLobbyAdapter.LobbyViewHolder>() {
    private val lobbies = ArrayList<Lobby>()

    fun setLobbies(lobbies:Collection<Lobby>){
        this.lobbies.clear()
        this.lobbies.addAll(lobbies)
        notifyDataSetChanged()
    }

    interface LobbyListener{
        fun onLobbyClicked(lobby: Lobby)
    }

    inner class LobbyViewHolder(private val binding: LobbyRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init{
            binding.root.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            callBack.onLobbyClicked(lobbies[adapterPosition])
        }

        fun bind(lobby: Lobby) {
            binding.lobbyName.text = lobby.lobby_name
            binding.lobbyPlayersCount.text = "Players : ${lobby.lobby_players.size}/${lobby.game.max_players}"
            binding.lobbyLocation.text = lobby.lobby_location

            binding.gameName.text = lobby.game.name
            Glide.with(binding.root).load(lobby.game.image_url).circleCrop().into(binding.lobbyGameImage)
            binding.lobbyDate.text = lobby.lobby_date
            binding.lobbyHaveGame.setImageResource(haveGame(lobby))

            }
        fun haveGame(lobby: Lobby): Int {
            return if(lobby.lobby_have_game == true)
                R.drawable.have_game
            else R.drawable.dont_have
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LobbyViewHolder(LobbyRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) =
        holder.bind(lobbies[position])

    override fun getItemCount() = lobbies.size
}
