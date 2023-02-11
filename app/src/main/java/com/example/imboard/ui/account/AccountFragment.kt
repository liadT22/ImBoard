package com.example.imboard.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.imboard.R
import com.example.imboard.databinding.FragmentAccountBinding
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.repository.FirebaseImpl.FireBaseStorageRepository
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.api.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import il.co.syntax.myapplication.util.Resource
import java.io.InputStream


class AccountFragment : Fragment() {

    private var binding : FragmentAccountBinding by autoCleared()
    private val viewModel: AccountViewModel by viewModels{
        AccountViewModel.AccountViewModelFactory(AuthRepositoryFirebase(),
            FireBaseStorageRepository()
        )
    }
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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        viewModel.currentUser.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.accountProgressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.accountProgressBar.isVisible = false
                    binding.accountUsername.text = it.data?.name
                    binding.accountEmail.text = it.data?.email
                }
                is Resource.Error -> {
                    binding.accountProgressBar.isEnabled = true
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.userPhoto.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.accountProgressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.accountProgressBar.isVisible = false
//                    binding.accountProfilePhoto.setImageURI(it.data)
                    Glide.with(this)
                        .load(it.data).circleCrop()
                        .into(binding.accountProfilePhoto)
                }
                is Resource.Error -> {
                    binding.accountProgressBar.isEnabled = true
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initAccunt(userName: TextView, userEmail:TextView, userPhoto:ImageView){
        val currentUser = FirebaseAuth.getInstance().currentUser

        binding.accountEmail.text = currentUser?.email
        val firebaseStorage = FirebaseStorage.getInstance()
        //val storageReference = firebaseStorage.getReference("files/~2Fimages/${currentUser.uid}")
//        val imageUri = storageReference.downloadUrl
//            .addOnSuccessListener { uri ->
//                val imageUri = uri
//                binding.accountProfilePhoto.setImageURI(imageUri)
//            }
//            .addOnFailureListener { exception ->
//                // Handle any errors that occur
//            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
