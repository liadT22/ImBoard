package com.example.imboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imboard.R
import com.example.imboard.databinding.FragmentSearchBinding
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding by autoCleared()

    //Lobby class variables
    lateinit var lobbysImageId: Array<Int>
    lateinit var lobbysNames: Array<String>
    lateinit var maxPlayers: Array<Int>
    lateinit var minPlayers: Array<Int>
    lateinit var locations: Array<String>
    lateinit var dates: Array<String>
    lateinit var have_game: Array<Boolean>


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


        //TODO delete code when adding API logic
        lobbysImageId = arrayOf(
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
        )
        lobbysNames = arrayOf(
            "the cool guys",
            "guys cool",
            "the boyz",
            "the cool guys",
            "guys cool",
            "the boyz",
            "the cool guys",
            "guys cool",
            "the boyz",
            "so cool gamers"
        )
        maxPlayers = arrayOf(6, 4, 8, 2, 3, 6, 4, 8, 2, 3)
        minPlayers = arrayOf(2, 2, 3, 1, 2, 3, 2, 2, 3, 2)
        locations = arrayOf(
            "Rishon Lezion",
            "Haifa",
            "Tel Aviv",
            "Rehovot",
            "Rishon Lezion",
            "Haifa",
            "Tel Aviv",
            "Rehovot",
            "Rishon Lezion",
            "Haifa"
        )
        dates = arrayOf(
            "02/09/23",
            "05/09/23",
            "03/09/23",
            "15/09/23",
            "03/10/23",
            "02/09/23",
            "05/09/23",
            "03/09/23",
            "15/09/23",
            "03/10/23"
        )
        have_game = arrayOf(true, false, true, true, false, true, false, true, true, false)
        //TODO delete code when adding API logic
        //TODO Read from FireBase the first 10~ lobbys in the list
//        lobbysImageId = //TODO add images from API
//        lobbysNames = //TODO add images from API
//        maxPlayers = //TODO add images from API
//        minPlayers = //TODO add images from API
//        locations =//TODO add images from API
//        dates = //TODO add images from API
//        have_game = //TODO add images from API

        binding.searchLobbiesRecycler.adapter = LobbyAdapterFix(LobbyManager.Lobbys)
        binding.searchLobbiesRecycler.layoutManager = LinearLayoutManager(requireContext())

        addLobbysFakeData() //TODO delete code when adding API logic

        return binding.root
    }

    //TODO delete code when adding API logic
    private fun addLobbysFakeData() {
        for (i in lobbysImageId.indices) {
            val lobby = Lobby(
                lobbysImageId[i],
                lobbysNames[i],
                maxPlayers[i],
                minPlayers[i],
                locations[i],
                dates[i],
                have_game[i]
            )
            LobbyManager.addLobby(lobby)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}