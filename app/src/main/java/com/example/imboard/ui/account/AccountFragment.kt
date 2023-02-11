package com.example.imboard.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.imboard.R
import com.example.imboard.databinding.FragmentAccountBinding
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AccountFragment : Fragment() {

    private var binding : FragmentAccountBinding by autoCleared()
    private val authRep: AuthRepositoryFirebase = AuthRepositoryFirebase()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.signOutBtn.setOnClickListener {
            authRep.logout()
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.action_accountFragment_to_registerOrLoginScreenFragment)

        }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener()
        {

            when (it.itemId) {
                R.id.ic_search -> findNavController().navigate(R.id.action_accountFragment_to_searchFragment)
                R.id.ic_addRoom -> findNavController().navigate(R.id.action_accountFragment_to_newLobbyFragment)
                R.id.ic_shop -> findNavController().navigate(R.id.action_accountFragment_to_shopFragment)
            }
            true
        }
        initAccunt()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
    }

    private fun initAccunt(){
        val currentUser = FirebaseAuth.getInstance().currentUser

        binding.accountUsername.text = currentUser
        binding.accountEmail.text = currentUser?.email

        val firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.getReference("files/~2Fimages/${currentUser?.uid}")
        val imageUri = storageReference.downloadUrl
            .addOnSuccessListener { uri ->
                val imageUri = uri
                binding.accountProfilePhoto.setImageURI(imageUri)
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}