package com.example.imboard.ui.lobby_screen

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.imboard.R
import com.example.imboard.databinding.LobbyScreenBinding
import com.example.imboard.model.Lobby
import il.co.syntax.myapplication.util.Error
import com.example.imboard.model.User
import com.example.imboard.ui.all_lobbies.AllLobbyAdapter
import com.example.imboard.ui.new_lobby.NewLobbyAdapter
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.myapplication.util.Loading
import il.co.syntax.myapplication.util.Success

@AndroidEntryPoint
class LobbyScreen : Fragment() {
    private var binding: LobbyScreenBinding by autoCleared()
    private val viewModel: LobbyScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LobbyScreenBinding.inflate(inflater, container, false)
        binding.accountRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.accountRecycler.adapter =
            LobbyScreenAdapter(object : LobbyScreenAdapter.AccountListener {
                override fun onAccountClicked(user: User) {
                    val bottomSheetDialog = BottomSheetDialog(requireContext())
                    bottomSheetDialog.setContentView(R.layout.account_detail_dialog)
                    bottomSheetDialog.findViewById<TextView>(R.id.username_dialog)?.text = user.name
                    bottomSheetDialog.findViewById<TextView>(R.id.email_dialog)?.text = user.email
                    Glide.with(requireActivity()).load(user.uri).into(bottomSheetDialog.findViewById<ImageView>(R.id.profile_photo_dialog)!!)
                    bottomSheetDialog.show()
                }
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var lobby: Lobby? = arguments?.getParcelable<Lobby>("lobby")
        var currentUser: User = User()

        viewModel.setLobby(lobby)

        viewModel.currentUser.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Success -> {
                    currentUser = it.status.data!!
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                }
            }
            binding.joinLobbyBtn.setOnClickListener {
                if (lobby!!.lobby_players.contains(currentUser)) {
                    lobby!!.lobby_players.remove(currentUser)
                } else {
                    lobby!!.lobby_players.add(currentUser)
                }
                viewModel.setLobby(lobby)
            }

            viewModel.currentLobby.observe(viewLifecycleOwner) {
                when (it.status) {
                    is Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Success -> {
                        binding.progressBar.isVisible = false
                        lobby = it.status.data
                        (binding.accountRecycler.adapter as LobbyScreenAdapter).setUsers(ArrayList(it.status.data?.lobby_players))
                        binding.lobbyDateLobbyRoom.text =
                            "${it.status.data?.lobby_date} - ${it.status.data?.lobby_time}"
                        binding.lobbyLocationLobbyRoom.text = it.status.data?.lobby_location
                        binding.lobbyGameName.text = it.status.data?.game?.name
                        binding.lobbyPlayerCount.text =
                            "Players: ${it.status.data?.lobby_players?.size}/${it.status.data?.game?.max_players}"
                        binding.lobbyName.text = it.status.data?.lobby_name
                        Glide.with(binding.root).load(it.status.data?.game?.image_url)
                            .into(binding.lobbyGameIcon)
                        if (lobby!!.lobby_players[0].id == currentUser.id){
                            binding.joinLobbyBtn.isVisible = false
                        }else if(lobby!!.lobby_players.contains(currentUser)){
                            binding.joinLobbyBtn.text = "Leave"
                            binding.joinLobbyBtn.isClickable = true
                        }else if (lobby!!.lobby_players.size == lobby!!.game.max_players){
                            binding.joinLobbyBtn.text = "Lobby full"
                            binding.joinLobbyBtn.isClickable = false
                        }else{
                            binding.joinLobbyBtn.text = "Join"
                            binding.joinLobbyBtn.isClickable = true
                        }
                    }
                    is Error -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
            (binding.accountRecycler.adapter as LobbyScreenAdapter).setUsers(ArrayList(lobby?.lobby_players))
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
                View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}