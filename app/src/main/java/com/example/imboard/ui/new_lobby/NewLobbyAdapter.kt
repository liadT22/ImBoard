package com.example.imboard.ui.new_lobby

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imboard.R
import com.example.imboard.databinding.GameCardRecyclerViewBinding
import com.example.imboard.databinding.LobbyRecyclerViewBinding
import com.example.imboard.model.Game
import com.example.imboard.model.Lobby
import com.example.imboard.ui.all_lobbies.AllLobbyAdapter

class NewLobbyAdapter (val callBack: GameListener) :
    RecyclerView.Adapter<NewLobbyAdapter.LobbyViewHolder>() {
    private val games = ArrayList<Game>()

    fun setGames(games:Collection<Game>){
        this.games.clear()
        this.games.addAll(games)
        notifyDataSetChanged()
    }
    interface GameListener{
        fun onGameClicked(game:Game)
    }

    inner class LobbyViewHolder(private val binding: GameCardRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init{
            binding.root.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            callBack.onGameClicked(games[adapterPosition])
        }

        fun bind(game: Game) {
            Glide.with(binding.root).load(game.image_url).into(binding.gameImage)
            binding.gameName.text = game.name
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LobbyViewHolder(GameCardRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) =
        holder.bind(games[position])

    override fun getItemCount() = games.size

}