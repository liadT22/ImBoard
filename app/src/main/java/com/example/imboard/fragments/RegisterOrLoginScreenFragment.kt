package com.example.imboard.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.imboard.Communicator
import com.example.imboard.MainActivity
import com.example.imboard.R
import com.example.imboard.databinding.FragmentRegisterOrLoginScreenBinding
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView

class RegisterOrLoginScreenFragment : Fragment() {

    private var binding : FragmentRegisterOrLoginScreenBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchFragment = SearchFragment()

        binding = FragmentRegisterOrLoginScreenBinding.inflate(inflater,container,false)

        var flag = arguments?.getBoolean("flag_userLogedin")



        binding.loginBtn.setOnClickListener{
            findNavController().navigate(R.id.action_registerOrLoginScreenFragment_to_searchFragment)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
            flag = true
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerOrLoginScreenFragment_to_registerFragment)
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