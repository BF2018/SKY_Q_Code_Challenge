package com.example.sky_q_code_challenge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sky_q_code_challenge.misc.Preferences
import com.example.sky_q_code_challenge.network.ApiClient

class MoviesViewModelFactory(private val api: ApiClient, private val pref: Preferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(api, pref) as T
    }

}