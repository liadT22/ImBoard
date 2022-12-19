package com.example.imboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.imboard.fragments.AccountFragment
import com.example.imboard.fragments.NewLobbyFragment
import com.example.imboard.fragments.SearchFragment
import com.example.imboard.fragments.ShopFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val accountFragment = AccountFragment()
        val newLobbyFragment = NewLobbyFragment()
        val searchFragment = SearchFragment()
        val shopFragment = ShopFragment()
        val bottom_navigation = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)

        makeCurrentFragment(searchFragment)
        var currentFragment : Fragment = searchFragment
        var toFragment : Fragment

        bottom_navigation.setOnNavigationItemSelectedListener {


            when (it.itemId){
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_addRoom -> {
                    toFragment = newLobbyFragment
                    currentFragment = newLobbyFragment
                }
                R.id.ic_account -> makeCurrentFragment(accountFragment)
                R.id.ic_shop -> makeCurrentFragment(shopFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//        replace(R.id.fl_wrapper,fragment)
//        commit()
//        }

        supportFragmentManager.beginTransaction().apply {
            Log.i("fragment",fragment.toString())
            setCustomAnimations(
                R.anim.from_left,
                R.anim.to_right
            )
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
}