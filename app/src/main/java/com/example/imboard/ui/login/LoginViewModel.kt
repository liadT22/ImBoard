package com.example.imboard.ui.login

import androidx.lifecycle.*
import com.example.imboard.R
import com.example.imboard.model.User
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRep : AuthRepository) :ViewModel() {
    private val _userSignInStatus = MutableLiveData<Resource<User>>()
    val userSignInStatus: LiveData<Resource<User>> = _userSignInStatus

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>> = _currentUser
    init {
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading())
            _currentUser.postValue(authRep.currentUser())
        }
    }
    fun signInUser(userEmail:String, userPassword:String){
        if(userEmail.isEmpty()||userPassword.isEmpty()){
            _userSignInStatus.postValue(Resource.error("Please fill the entire form"))
        }else{
            _userSignInStatus.postValue(Resource.loading())
            viewModelScope.launch {
                val loginResult = authRep.login(userEmail,userPassword)
                _userSignInStatus.postValue(loginResult)
            }
        }
    }
}