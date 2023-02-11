package com.example.imboard.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    val userId = FirebaseAuth.getInstance().currentUser!!.uid

    //val storageReference = FirebaseStorage.getInstance().getReference("images/$userId")
    private var binding: FragmentAccountBinding by autoCleared()
    private val authRep: AuthRepositoryFirebase = AuthRepositoryFirebase()
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    private var imageUri: Uri? = null
    private val pickImageResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                binding.accountProfilePhoto.setImageURI(it)
                requireActivity().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = it
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        GlobalScope.launch(Dispatchers.Main) {
            initAccountFragment()
        }

        binding.signOutBtn.setOnClickListener {
            authRep.logout()
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.action_accountFragment_to_registerOrLoginScreenFragment)

        }
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setOnNavigationItemSelectedListener()
            {

                when (it.itemId) {
                    R.id.ic_search -> findNavController().navigate(R.id.action_accountFragment_to_searchFragment)
                    R.id.ic_addRoom -> findNavController().navigate(R.id.action_accountFragment_to_newLobbyFragment)
                    R.id.ic_shop -> findNavController().navigate(R.id.action_accountFragment_to_shopFragment)
                }
                true
            }

        binding.accountChangePhotoBtn.setOnClickListener {
            val userID = FirebaseAuth.getInstance().currentUser?.uid
            if (userID != null) {
                selectPhoto()
                replaceProfilePhoto(userID, imageUri)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun selectPhoto() {
        pickImageResultLauncher.launch(arrayOf("image/*"))
    }

    private fun replaceProfilePhoto(userId: String, newImageUri: Uri?) {
        val fileReference = FirebaseStorage.getInstance().reference.child("images/$userId")
        val uploadTask = newImageUri?.let { fileReference.putFile(it) }
        if (uploadTask != null) {
            uploadTask.addOnFailureListener {
                Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                fileReference.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val userRef =
                        FirebaseFirestore.getInstance().collection("users").document(userId)
                    userRef.update("profileImageUrl", downloadUrl.toString())
                        .addOnSuccessListener {
                            Toast.makeText(context, "Profile photo updated!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Failed to update profile photo!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }

    private suspend fun initAccountFragment() {

        val currentUserID = authRep.currentUser()
        binding.accountEmail.text = currentUserID.data?.email
        binding.accountUsername.text = currentUserID.data?.name
        val fileReference = storageReference.child("images/${currentUserID.toString()}")

        fileReference.downloadUrl.addOnSuccessListener {
            // Use the download URL to display the profile photo
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to retrieve profile photo!", Toast.LENGTH_SHORT).show()
        }
    }
}
