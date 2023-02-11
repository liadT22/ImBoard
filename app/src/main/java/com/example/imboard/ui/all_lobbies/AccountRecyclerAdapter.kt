package com.example.imboard.ui.all_lobbies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.imboard.databinding.AccountLobbyRecyclerBinding
import com.example.imboard.model.AccountRecycler
import com.example.imboard.model.User

class AccountRecyclerAdapter(val items: MutableList<AccountRecycler>, lobbyPlayers: ArrayList<User>) : RecyclerView.Adapter<AccountViewHolder>() {

    private val lobbyPlayers = lobbyPlayers
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(AccountLobbyRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class AccountViewHolder(private val binding: AccountLobbyRecyclerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(items: AccountRecycler) {
        binding.accountPhotoRecycler.setImageURI(items.profilePhoto.toUri())
        binding.accountUsernameRecycler.text = items.userName
    }

}
