package com.example.imboard.ui.all_lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imboard.R
import com.example.imboard.databinding.FragmentSearchBinding
import com.example.imboard.model.Lobby
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.repository.FirebaseImpl.GameRepository
import com.example.imboard.repository.FirebaseImpl.LobbyRepositoryFirebase
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.myapplication.util.Loading
import il.co.syntax.myapplication.util.Resource
import il.co.syntax.myapplication.util.Success
import il.co.syntax.myapplication.util.Error

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding by autoCleared()
    private val viewModel : AllLobbiesViewModel by viewModels()
//    private val viewModel : AllLobbiesViewModel by viewModels{
//        AllLobbiesViewModel.AllLobbiesViewModelFactory(
//            LobbyRepositoryFirebase(),
//            GameRepository()
//        )
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener()
        {

            when (it.itemId) {
                R.id.ic_account -> findNavController().navigate(R.id.action_searchFragment_to_accountFragment)
                R.id.ic_addRoom -> findNavController().navigate(R.id.action_searchFragment_to_newLobbyFragment)
                R.id.ic_shop -> findNavController().navigate(R.id.action_searchFragment_to_shopFragment)
            }
            true
        }
        binding = FragmentSearchBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchLobbiesRecycler.layoutManager = LinearLayoutManager(requireContext())
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        binding.searchLobbiesRecycler.adapter = AllLobbyAdapter(object :AllLobbyAdapter.LobbyListener{
            override fun onLobbyClicked(lobby: Lobby) {
                val bundle = Bundle()
                bundle.putParcelable("lobby", lobby)
                findNavController().navigate(R.id.action_searchFragment_to_lobbyScreenFragment, bundle)
            }
        })

        viewModel.lobbiesStatus.observe(viewLifecycleOwner){
            when(it.status){
                is Loading ->{
                    binding.progressBar.isVisible = true
                }
                is Success -> {
                    binding.progressBar.isVisible = false
                    (binding.searchLobbiesRecycler.adapter as AllLobbyAdapter).setLobbies(it.status.data!!)
                }
                is Error ->{
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}