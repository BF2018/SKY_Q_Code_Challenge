package com.example.sky_q_code_challenge.ui

import android.app.Application

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance : Application? = null
        fun getInstance() = instance
    }


}