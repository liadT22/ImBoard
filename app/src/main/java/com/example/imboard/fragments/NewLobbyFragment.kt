package com.example.imboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.imboard.R
import com.example.imboard.databinding.FragmentNewLobbyBinding
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewLobbyFragment : Fragment() {
    private var binding : FragmentNewLobbyBinding by autoCleared()
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}