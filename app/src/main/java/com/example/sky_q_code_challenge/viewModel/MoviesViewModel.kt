package com.example.sky_q_code_challenge.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sky_q_code_challenge.data.Movies
import com.example.sky_q_code_challenge.extensions.getAppContext
import com.example.sky_q_code_challenge.extensions.runApiCall
import com.example.sky_q_code_challenge.network.ApiClient


class MoviesViewModel : ViewModel() {

    var movies = MutableLiveData<Movies>()


   fun getDataFromNetwork() {
       ApiClient.callApi().runApiCall({ listOfMovies ->
        movies.value = listOfMovies
       },{error->
           error.printStackTrace()
           Toast.makeText(getAppContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
       })
    }

    fun getObservable() = movies

}