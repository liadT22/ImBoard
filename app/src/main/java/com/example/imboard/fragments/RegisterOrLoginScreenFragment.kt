package com.example.imboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        binding = FragmentRegisterOrLoginScreenBinding.inflate(inflater,container,false)
        val bundle = arguments

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