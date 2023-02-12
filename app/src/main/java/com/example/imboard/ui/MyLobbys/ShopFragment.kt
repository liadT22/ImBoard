package com.example.imboard.ui.MyLobbys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imboard.R
import com.example.imboard.databinding.FragmentShopBinding
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.repository.FirebaseImpl.LobbyRepositoryFirebase
import com.example.imboard.ui.all_lobbies.AllLobbiesViewModel
import com.example.imboard.ui.all_lobbies.AllLobbyAdapter
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import il.co.syntax.myapplication.util.Resource

class ShopFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var binding : FragmentShopBinding by autoCleared()
    private val viewModel : MyLobbysViewModel by viewModels{
        MyLobbysViewModel.MyLobbysViewModelFactory(AuthRepositoryFirebase())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()

        binding = FragmentShopBinding.inflate(inflater,container,false)


        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener()
        {

            when (it.itemId) {
                R.id.ic_account -> findNavController().navigate(R.id.action_shopFragment_to_accountFragment)
                R.id.ic_search -> findNavController().navigate(R.id.action_shopFragment_to_searchFragment)
                R.id.ic_addRoom -> findNavController().navigate(R.id.action_shopFragment_to_newLobbyFragment)
            }
            true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE

        binding.searchLobbiesRecycler.layoutManager = LinearLayoutManager(requireContext())
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        binding.searchLobbiesRecycler.adapter = AllLobbyAdapter(object : AllLobbyAdapter.LobbyListener{
            override fun onLobbyClicked(lobby: Lobby) {
                val bundle = Bundle()
                bundle.putParcelable("lobby", lobby)
                findNavController().navigate(R.id.action_searchFragment_to_lobbyScreenFragment, bundle)
            }
        })

        viewModel.currentUserLobbys.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    //need to change to user lobbys list
                        (binding.searchLobbiesRecycler.adapter as AllLobbyAdapter).setLobbies(it.data!!)
                }
                is Resource.Error ->{
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}