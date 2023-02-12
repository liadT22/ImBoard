package com.example.imboard.ui.account

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.imboard.R
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.FireBaseStorageRepository
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val authRep: AuthRepository,
    private val fireStorageRep: FireBaseStorageRepository
) : ViewModel() {
    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>> = _currentUser

    private val _userPhoto = MutableLiveData<Resource<Uri>>()
    val userPhoto: LiveData<Resource<Uri>> = _userPhoto

    init {
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading())
            if (authRep.currentUser().status.data == null) {
                _currentUser.postValue(Resource.error("GuestMode On"))
            } else {
                _currentUser.postValue(authRep.currentUser())
                getUserPhoto()
            }
        }
    }

    fun logOut(){
        authRep.logout()
    }

    private fun getUserPhoto() {
        viewModelScope.launch {
            _userPhoto.postValue(Resource.loading())
            if (authRep.currentUser().status.data == null) {
                _userPhoto.postValue(Resource.error("GuestMode On"))
            } else {
                _userPhoto.postValue(fireStorageRep.getUserPhoto(authRep.currentUser().status.data!!.id))
            }
        }
    }
}