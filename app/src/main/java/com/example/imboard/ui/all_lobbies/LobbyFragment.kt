package com.example.imboard.ui.all_lobbies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.imboard.databinding.LobbyScreenBinding
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.repository.FirebaseImpl.LobbyRepositoryFirebase
import com.example.imboard.util.autoCleared

class LobbyFragment : Fragment() {
    private var binding : LobbyScreenBinding by autoCleared()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LobbyScreenBinding.inflate(inflater, container, false)

        Glide.with(binding.root).load("C:\\Users\\PC\\StudioProjects\\ImBoard\\app\\src\\main\\res\\drawable-v24\\cat_photo.png").circleCrop().into(binding.lobbyGameIcon)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}