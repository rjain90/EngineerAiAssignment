package com.rjain90.engineeraiassignment.ui

import android.app.Application
import com.rjain90.engineeraiassignment.data.network.NetworkManager

/**
 * Created by rishabhjain on 3/29/17.
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkManager.initialize()

        instance = this
    }

    companion object {
        var instance: MyApplication? = null
            private set
    }
}
