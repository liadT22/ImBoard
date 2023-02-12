package com.example.imboard.ui.account

import android.app.AlertDialog
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
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.myapplication.util.Loading
import il.co.syntax.myapplication.util.Resource
import il.co.syntax.myapplication.util.Success
import il.co.syntax.myapplication.util.Error
import java.io.InputStream

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var binding : FragmentAccountBinding by autoCleared()
    private val viewModel: AccountViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.signOutBtn.setOnClickListener {
            viewModel.logOut()
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.action_accountFragment_to_registerOrLoginScreenFragment)
        }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener()
        {

            when (it.itemId) {
                R.id.ic_search -> findNavController().navigate(R.id.action_accountFragment_to_searchFragment)
                R.id.ic_addRoom -> findNavController().navigate(R.id.action_accountFragment_to_newLobbyFragment)
            }
            true
        }
        binding.termsAndConditionBtn.setOnClickListener{
            val builder = AlertDialog.Builder(context).setTitle(R.string.terms_and_conditions)
                .setMessage(R.string.term_and_conditions_string)
                .setPositiveButton(R.string.agree) { dialogInterface, it ->
                    dialogInterface.cancel()
                }
            builder.show()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        viewModel.currentUser.observe(viewLifecycleOwner){
            when(it.status){
                is Loading ->{
                    binding.accountProgressBar.isVisible = true
                }
                is Success -> {
                    binding.accountProgressBar.isVisible = false
                    binding.accountUsername.text = it.status.data?.name
                    binding.accountEmail.text = it.status.data?.email
                }
                is Error -> {
                    binding.accountProgressBar.isEnabled = true
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.userPhoto.observe(viewLifecycleOwner){
            when(it.status){
                is Loading ->{
                    binding.accountProgressBar.isVisible = true
                }
                is Success -> {
                    binding.accountProgressBar.isVisible = false
                    Glide.with(this)
                        .load(it.status.data).circleCrop()
                        .into(binding.accountProfilePhoto)
                }
                is Error -> {
                    binding.accountProgressBar.isEnabled = true
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}
