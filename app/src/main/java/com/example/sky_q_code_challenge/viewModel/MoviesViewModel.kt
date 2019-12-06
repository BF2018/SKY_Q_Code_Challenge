package com.example.sky_q_code_challenge.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sky_q_code_challenge.data.Movies
import com.example.sky_q_code_challenge.extensions.getAppContext
import com.example.sky_q_code_challenge.extensions.runApiCall
import com.example.sky_q_code_challenge.misc.Preferences
import com.example.sky_q_code_challenge.network.ApiClient


class MoviesViewModel : ViewModel() {

    var movies = MutableLiveData<Movies>()
    var pref =  Preferences(getAppContext())

   private fun getDataFromNetwork() {
       ApiClient.callApi().runApiCall({ listOfMovies ->
        movies.value = listOfMovies
           setApiDataToSharedPref(listOfMovies)
       },{error->
           error.printStackTrace()
           Toast.makeText(getAppContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
       })
    }

    fun getObservable() = movies

    private fun setApiDataToSharedPref(movies: Movies){
        pref.setMoviesData("savedMovieList",movies)
        pref.setTimerForCache()
    }

    fun getMoviesData(){
        if (pref.preferences.getLong("ExpiredDate", -1) >
            System.currentTimeMillis()){
            getDataFormSharedPref()
        }else
            pref.preferences.apply {
                edit().clear().apply()
            }
            getDataFromNetwork()
    }

    private fun getDataFormSharedPref(){
       movies.value = pref.getSavedMoviesData("savedMovieList")
    }
}