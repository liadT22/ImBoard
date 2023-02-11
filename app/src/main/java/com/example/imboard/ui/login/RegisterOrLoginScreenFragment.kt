package com.example.imboard.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.imboard.R
import com.example.imboard.databinding.FragmentRegisterOrLoginScreenBinding
import com.example.imboard.ui.all_lobbies.SearchFragment
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import il.co.syntax.myapplication.util.Resource

class RegisterOrLoginScreenFragment : Fragment() {

    private var binding : FragmentRegisterOrLoginScreenBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels{
        LoginViewModel.LoginViewModelFactory(AuthRepositoryFirebase())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchFragment = SearchFragment()

        binding = FragmentRegisterOrLoginScreenBinding.inflate(inflater,container,false)

        binding.loginBtn.setOnClickListener{
            viewModel.signInUser(binding.emailEtxt.editText?.text.toString(),
            binding.passwordEtxt.editText?.text.toString())

        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerOrLoginScreenFragment_to_registerFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userSignInStatus.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.loginBtn.isEnabled = false
                    binding.loginProgressBar.isVisible = true
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerOrLoginScreenFragment_to_searchFragment)
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.loginProgressBar.isVisible = false
                    binding.loginBtn.isEnabled = true
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.currentUser.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.loginProgressBar.isVisible = true
                    binding.loginBtn.isEnabled = false
                }
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_registerOrLoginScreenFragment_to_searchFragment)
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.loginProgressBar.isVisible = false
                    binding.loginBtn.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}