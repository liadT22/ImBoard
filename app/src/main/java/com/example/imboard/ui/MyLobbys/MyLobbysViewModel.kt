package com.example.imboard.ui.MyLobbys

import androidx.lifecycle.*
import com.example.imboard.model.Lobby
import com.example.imboard.repository.FirebaseImpl.AuthRepository
import com.example.imboard.repository.FirebaseImpl.LobbiesRepository
import com.example.imboard.ui.all_lobbies.AllLobbiesViewModel
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.launch

class MyLobbysViewModel(private val authRep: AuthRepository) : ViewModel() {

    private val _currentUserLobbys : MutableLiveData<Resource<ArrayList<Lobby>>> = MutableLiveData()
    val currentUserLobbys : LiveData<Resource<ArrayList<Lobby>>> = _currentUserLobbys

    init {
        getUserLobbys()
    }

    private fun getUserLobbys(){
        viewModelScope.launch{
            _currentUserLobbys.postValue(Resource.Loading())
            if(authRep.currentUser().data?.lobbies == null){
                _currentUserLobbys.postValue(Resource.Error("No lobbys"))
            }else{
                _currentUserLobbys.postValue(Resource(authRep.currentUser().data!!.lobbies))
            }
        }
    }
    class MyLobbysViewModelFactory(val authRep: AuthRepository):
        ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyLobbysViewModel(authRep) as T
        }
    }
}