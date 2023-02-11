package com.example.imboard.ui.recycleViewAccounts

import com.example.imboard.model.User

data class Accounts (val accounts: ArrayList<User>)

object UserManager {

    val accounts: MutableList<Accounts> = mutableListOf()

    fun add(account: Accounts){
        accounts.add(account)
    }

    fun remove(index: Int){
        accounts.removeAt(index)
    }

}