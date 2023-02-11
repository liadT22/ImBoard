package com.example.imboard.ui.all_lobbies

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.imboard.databinding.AccountLobbyRecyclerBinding
import com.example.imboard.model.AccountRecycler
import com.example.imboard.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AccountRecyclerAdapter(val items: MutableList<AccountRecycler>, lobbyPlayers: ArrayList<User>) : RecyclerView.Adapter<AccountViewHolder>() {

    private val lobby_Players = lobbyPlayers
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(AccountLobbyRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) =
        holder.bind(items[position],lobby_Players)

    override fun getItemCount(): Int = items.size
}

class AccountViewHolder(private val binding: AccountLobbyRecyclerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(items: AccountRecycler, lobby_Players: ArrayList<User>) {
        //TODO download from firebase URI
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("files/images/${itemId}")

        val MAX_MEGABYTE: Long = 1024 * 1024 * 108
        imageRef.getBytes(MAX_MEGABYTE).addOnSuccessListener { bytes ->
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding.accountPhotoRecycler.setImageBitmap(bitmap)
        }.addOnFailureListener {
            // Handle any errors
        }


        binding.accountUsernameRecycler.text = items.userName
    }

}
