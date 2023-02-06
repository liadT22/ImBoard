package com.example.imboard.ui.register

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.imboard.R
import com.example.imboard.databinding.FragmentRegisterScreenBinding
import com.example.imboard.repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.imboard.util.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView
import il.co.syntax.myapplication.util.Resource


class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterScreenBinding by autoCleared()
    private val viewModel: RegisterViewModel by viewModels(){
        RegisterViewModel.RegisterViewModelFactory(AuthRepositoryFirebase())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)

        val builder = createThermsAlertDialog()
        binding.regSubmitBtn.isActivated = false

        //popup alertdialog of therms when press checkbox
        binding.regTacCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(binding.regTacCheckBox.isChecked === true)
                builder?.show()
            if(binding.regTacCheckBox.isChecked === true)
                binding.regSubmitBtn.isActivated = true
            else binding.regSubmitBtn.isActivated = false
        }

        emailFocusListener()
        passwordFocusListener()
        nicknameFocusListener()

        binding.regSubmitBtn.setOnClickListener {
            submitRegister()

        }

        return binding.root
    }

    fun userChoice(choice: String?) {
        if (choice === "Agree")
            binding.regTacCheckBox.isChecked = true
        else if (choice === "DisAgree")
            binding.regTacCheckBox.isChecked = false
    }

    private fun createThermsAlertDialog(): AlertDialog.Builder? {
        return AlertDialog.Builder(context).setTitle("Terms and conditions")
            .setMessage("bla")
            .setPositiveButton("Agree") { dialogInterface, it ->
                userChoice("Agree")
                dialogInterface.cancel()
            }
            .setNegativeButton("DisAgree") { dialogInterface, it ->
                userChoice("DisAgree")
                dialogInterface.cancel()
            }
    }

    private fun submitRegister()
    {
        binding.regEmailContainer.helperText = validEmail()
        binding.regPasswordContainer.helperText = validPassword()
        binding.regNickContainer.helperText = validNickname()

        val validEmail = binding.regEmailContainer.helperText == null
        val validPassword = binding.regPasswordContainer.helperText == null
        val validNickname = binding.regNickContainer.helperText == null

        if (validEmail && validPassword && validNickname && binding.regTacCheckBox.isChecked === true) {
            viewModel.createUser(binding.regNickEditTxt.text.toString(),
            binding.regEmailEditTxt.text.toString(),
            binding.regPasswordEditTxt.text.toString())
        }
        else
            invalidForm()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userRegistrationStatus.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.regSubmitBtn.isEnabled = false
                    binding.registerProgress.isVisible = true
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_searchFragment)
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.registerProgress.isVisible = false
                    binding.regSubmitBtn.isEnabled = true
                }
            }
        }
    }

    private fun invalidForm()
    {
        var message = ""
        if(binding.regEmailContainer.helperText != null)
            message += "\n\nEmail: " + binding.regEmailContainer.helperText
        if(binding.regPasswordContainer.helperText != null)
            message += "\n\nPassword: " + binding.regPasswordContainer.helperText
        if(binding.regNickContainer.helperText != null)
            message += "\n\nNickname: " + binding.regNickContainer.helperText
        if(!binding.regTacCheckBox.isChecked)
            message += "\n\nPlease check Terms and Conditions box"

        AlertDialog.Builder(context)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                // do nothing
            }
            .show()
    }

    private fun resetForm()
    {
        var message = "Email: " + binding.regEmailEditTxt.text
        message += "\nPassword: " + binding.regPasswordEditTxt.text
        AlertDialog.Builder(context)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                binding.regEmailEditTxt.text = null
                binding.regPasswordEditTxt.text = null
                binding.regNickEditTxt.text = null

                binding.regEmailContainer.helperText = getString(R.string.required)
                binding.regPasswordContainer.helperText = getString(R.string.required)
                binding.regNickContainer.helperText = getString(R.string.required)
            }
            .show()

    }

    private fun emailFocusListener()
    {
        binding.regEmailEditTxt.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.regEmailContainer.helperText = validEmail()
            }
        }
    }
    private fun nicknameFocusListener(){
        binding.regNickEditTxt.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.regNickContainer.helperText = validNickname()
            }
        }
    }
    private fun validNickname(): String?
    {
        val nickname = binding.regNickEditTxt.text.toString()
        if(nickname.length < 3)
            return "Minimum 3 Character Nickname"
        return null
    }

    private fun validEmail(): String?
    {
        val emailText = binding.regEmailEditTxt.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.regPasswordEditTxt.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.regPasswordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.regPasswordEditTxt.text.toString()
        if(passwordText.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }

        return null
    }

}
