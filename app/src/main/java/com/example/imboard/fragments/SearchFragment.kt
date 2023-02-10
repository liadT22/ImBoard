package com.example.imboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.navigation.fragment.findNavController
=======
import androidx.recyclerview.widget.ItemTouchHelper
>>>>>>> lobby-recycler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        //keep this code
        binding.searchLobbiesRecycler.adapter = LobbyAdapterFix(LobbyManager.Lobbys,object : LobbyAdapterFix.LobbyListener{
            override fun onLobbyClicked(index: Int) {
                TODO("add lobby fragment")
            }
        })
        binding.searchLobbiesRecycler.layoutManager = LinearLayoutManager(requireContext())
        addMovementToRecycler()


        addLobbysFakeData() //TODO delete code when adding API logic


        return binding.root
    }

    private fun addMovementToRecycler() {
        ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                TODO("Not yet implemented")
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
                //UPDATE all lobbys (refresh) binding.searchLobbiesRecycler.adapter!!.notifyDataSetChanged(viewHolder.adapterPosition)
                //UPDATE if we delete binding.searchLobbiesRecycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        }).attachToRecyclerView(binding.searchLobbiesRecycler)
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