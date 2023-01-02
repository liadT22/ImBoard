package com.example.imboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.imboard.databinding.LobbyScreenBinding

class LobbyScreenFragment : Fragment() {
    private var _binding : LobbyScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LobbyScreenBinding.inflate(inflater, container, false)

        Glide.with(binding.root).load("C:\\Users\\PC\\StudioProjects\\ImBoard\\app\\src\\main\\res\\drawable-v24\\cat_photo.png").circleCrop().into(binding.gameIcon)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}