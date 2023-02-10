package com.example.imboard.ui.new_lobby

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.imboard.R
import com.example.imboard.databinding.FragmentNewLobbyBinding
import com.example.imboard.model.Game
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.repository.FirebaseImpl.LobbyRepositoryFirebase
import com.example.imboard.ui.all_lobbies.AllLobbiesViewModel
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.type.DateTime
import il.co.syntax.myapplication.util.Resource

class NewLobbyFragment : Fragment() {
    private var binding : FragmentNewLobbyBinding by autoCleared()
    private val viewModel : NewLobbyViewModel by viewModels {
        NewLobbyViewModel.NewLobbiesViewModelFactory(AuthRepositoryFirebase(), LobbyRepositoryFirebase())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewLobbyBinding.inflate(inflater,container,false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener()
        {

            when (it.itemId) {
                R.id.ic_account -> findNavController().navigate(R.id.action_newLobbyFragment_to_accountFragment)
                R.id.ic_search -> findNavController().navigate(R.id.action_newLobbyFragment_to_searchFragment)
                R.id.ic_shop -> findNavController().navigate(R.id.action_newLobbyFragment_to_shopFragment)
            }
            true
        }
        binding.createButtonNewLobby.setOnClickListener {
            val game = Game()
            val date = "now";
            viewModel.addLobby(binding.lobbyNameNewLbby.text.toString(),binding.locationNewLobby.text.toString()
            ,game, date ,binding.checkboxHaveGame.equals(true), binding.checkboxHomeHost.equals(true))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lobbiesStatus.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.newLobbyProgressBar.isVisible = true
                    binding.createButtonNewLobby.isEnabled = false
                }
                is Resource.Success -> {
                    binding.newLobbyProgressBar.isVisible = false
                    binding.createButtonNewLobby.isEnabled = true
                }
                is Resource.Error ->{
                    binding.newLobbyProgressBar.isVisible = false
                    binding.createButtonNewLobby.isEnabled = true
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.addLobbyStatus.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.newLobbyProgressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.newLobbyProgressBar.isVisible = false
                    Snackbar.make(binding.root, "Item Added!", Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    binding.newLobbyProgressBar.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}