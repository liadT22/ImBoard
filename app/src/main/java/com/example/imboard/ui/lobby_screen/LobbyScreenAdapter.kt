package com.example.imboard.ui.lobby_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imboard.databinding.AccountCardRecyclerViewBinding
import com.example.imboard.model.User

class LobbyScreenAdapter (val callBack: AccountListener) :
    RecyclerView.Adapter<LobbyScreenAdapter.LobbyScreenViewHolder>() {
    private val users = ArrayList<User>()

    fun setUsers(users:Collection<User>){
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    interface AccountListener{
        fun onAccountClicked(user: User)
    }

    inner class LobbyScreenViewHolder(private val binding : AccountCardRecyclerViewBinding):
            RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init{
            binding.root.setOnClickListener(this)

        }
        override fun onClick(v: View?) {
            callBack.onAccountClicked(users[adapterPosition])
        }

        fun bind(user: User) {
            Glide.with(binding.root).load(user.uri).circleCrop().into(binding.accountImage)
            binding.accountName.text = user.name
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        LobbyScreenViewHolder(AccountCardRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent, false))


    override fun onBindViewHolder(holder: LobbyScreenViewHolder, position: Int) =
        holder.bind(users[position])

    override fun getItemCount() = users.size

}