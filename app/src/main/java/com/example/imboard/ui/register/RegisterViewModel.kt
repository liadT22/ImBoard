package com.example.imboard.ui.register

import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.*
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
            "Empty strings"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            "Not a valid email"
        } else null
        error?.let {
            _userRegistrationStatus.postValue(Resource.error(it))
        }
        _userRegistrationStatus.value = Resource.loading()
        viewModelScope.launch {
            val registrationResult = repository.createUser(userName, userEmail, userPass)
            firebaseStorage.setUserPhoto(registrationResult.status.data!!.id,imageUri)
            _userRegistrationStatus.postValue(registrationResult)
        }
    }
}