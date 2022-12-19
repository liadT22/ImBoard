package com.example.imboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.imboard.fragments.AccountFragment
import com.example.imboard.fragments.NewLobbyFragment
import com.example.imboard.fragments.SearchFragment
import com.example.imboard.fragments.ShopFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountFragment = AccountFragment()
        val newLobbyFragment = NewLobbyFragment()
        val searchFragment = SearchFragment()
        val shopFragment = ShopFragment()
        val bottom_navigation = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)

        makeCurrentFragment(searchFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_addRoom -> makeCurrentFragment(newLobbyFragment)
                R.id.ic_account -> makeCurrentFragment(accountFragment)
                R.id.ic_shop -> makeCurrentFragment(shopFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
        replace(R.id.fl_wrapper,fragment)
        commit()
        }
}