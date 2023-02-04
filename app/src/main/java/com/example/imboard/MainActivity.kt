package com.example.imboard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.imboard.databinding.ActivityMainBinding
import com.example.imboard.fragments.*

class MainActivity : AppCompatActivity(), Communicator {
    lateinit var binding: ActivityMainBinding
    var flag_userLogedin = false

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

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_addRoom -> makeCurrentFragment(newLobbyFragment)
                R.id.ic_account -> makeCurrentFragment(accountFragment)
                R.id.ic_shop -> makeCurrentFragment(shopFragment)
            }
            true
        }
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        flag_userLogedin = true
        //binding.bottomNavigation.visibility = View.VISIBLE
    }


    private fun makeCurrentFragment(fragment: Fragment) =

        supportFragmentManager.beginTransaction().apply {
            Log.i("fragment", fragment.toString())
            //do slide to left/right
            setCustomAnimations(
                R.anim.from_left,
                R.anim.to_right
            )
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }

    override fun passDataCom(userFlag: Boolean) {
        //TODO : replace the false with if the user is loged in or not from the ROOM
        val bundle = Bundle()
        bundle.putBoolean("flag_userLogedin", flag_userLogedin)

        val transaction = this.supportFragmentManager.beginTransaction()
        val loginScreenFragment = RegisterOrLoginScreenFragment()
        loginScreenFragment.arguments = bundle

        transaction.replace(R.id.fragmentContainerView, loginScreenFragment).commit()

    }
}