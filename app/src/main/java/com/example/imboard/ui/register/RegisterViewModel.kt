package com.example.imboard.ui.register

import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.*
import com.example.imboard.R
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.FireBaseStorageRepository
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository, private val firebaseStorage : FireBaseStorageRepository): ViewModel() {

    private val _userRegistrationStatus = MutableLiveData<Resource<User>>()
    val userRegistrationStatus: LiveData<Resource<User>> = _userRegistrationStatus


    fun createUser(
        userName: String,
        userEmail: String,
        userPass: String,
        imageUri: Uri
    ) {
        val error = if (userEmail.isEmpty() || userName.isEmpty() || userPass.isEmpty()) {
            "Empty username or email or password"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            "Invalid Email Address"
        } else null
        error?.let {
            _userRegistrationStatus.postValue(Resource.error(it))
        }
        _userRegistrationStatus.value = Resource.loading()
        viewModelScope.launch {
            val registrationResult = repository.createUser(userName, userEmail, userPass, imageUri)
            firebaseStorage.setUserPhoto(registrationResult.status.data!!.id,imageUri)
            _userRegistrationStatus.postValue(registrationResult)
        }
    }

    fun validPassword(passwordText: String) :String?{
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

    fun validEmail(emailText :String) : String?{
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

    fun validNickName(nickname : String) : String?{
        if(nickname.length < 3)
            return "Minimum 3 Character Nickname"
        return null
    }
}