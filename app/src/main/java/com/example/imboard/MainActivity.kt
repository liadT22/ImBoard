package com.example.imboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.imboard.databinding.ActivityMainBinding
import com.example.imboard.fragments.*

class MainActivity : AppCompatActivity() {
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
        //TODO : replace the false with if the user is loged in or not from the ROOM
        var flag_userLogedin = false
        val bundle = Bundle()
        bundle.putBoolean("flag_userLogedin", flag_userLogedin)


        //show first fragment
        bottom_navigation.visibility = View.GONE



        if(flag_userLogedin == true) {
            bottom_navigation.visibility = View.VISIBLE
            makeCurrentFragment(searchFragment)
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
    }

    private fun makeCurrentFragment(fragment: Fragment) =

        supportFragmentManager.beginTransaction().apply {
            Log.i("fragment",fragment.toString())
            //do slide to left/right
            setCustomAnimations(
                R.anim.from_left,
                R.anim.to_right
            )
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
}