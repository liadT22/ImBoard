package com.example.imboard.model

import android.hardware.camera2.CameraManager.AvailabilityCallback
import android.net.Uri

data class AccountRecycler(val profilePhoto: String, val userName: String) {

    object ItemManager {
        val items: MutableList<AccountRecycler> = mutableListOf()

        fun add(item: AccountRecycler) {
            items.add(item)
        }

        fun remove(index: Int) {
            items.removeAt(index)
        }
    }
}