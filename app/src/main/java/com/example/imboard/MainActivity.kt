package com.example.imboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.imboard.databinding.ActivityMainBinding
import com.example.imboard.fragments.*
import com.example.imboard.ui.account.AccountFragment
import com.example.imboard.ui.all_lobbies.SearchFragment
import com.example.imboard.ui.login.RegisterOrLoginScreenFragment
import com.example.imboard.ui.new_lobby.NewLobbyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Communicator {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        val accountFragment = AccountFragment()
        val newLobbyFragment = NewLobbyFragment()
        val searchFragment = SearchFragment()
        val shopFragment = ShopFragment()
        val loginScreenFragment = RegisterOrLoginScreenFragment()
        val bottom_navigation = binding.bottomNavigation
        var started = true


        //show first fragment
        if (started) {
            bottom_navigation.visibility = View.GONE
            started = false
        }
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        //binding.bottomNavigation.visibility = View.VISIBLE
    }



    override fun passDataCom(userFlag: Boolean) {
        //TODO : replace the false with if the user is loged in or not from the ROOM
        val bundle = Bundle()

        val transaction = this.supportFragmentManager.beginTransaction()
        val loginScreenFragment = RegisterOrLoginScreenFragment()
        loginScreenFragment.arguments = bundle

        transaction.replace(R.id.fragmentContainerView, loginScreenFragment).commit()

    }
}