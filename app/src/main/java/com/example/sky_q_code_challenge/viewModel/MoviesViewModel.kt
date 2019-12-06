package com.example.sky_q_code_challenge.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sky_q_code_challenge.data.Movie
import com.example.sky_q_code_challenge.data.MovieResponse
import com.example.sky_q_code_challenge.enums.ProcessStep
import com.example.sky_q_code_challenge.misc.Preferences
import com.example.sky_q_code_challenge.network.ApiClient
import io.reactivex.disposables.CompositeDisposable


class MoviesViewModel(private val apiClient: ApiClient, private val pref: Preferences) :
    ViewModel() {

    private val disposable = CompositeDisposable()
    private val fullMovieList = mutableListOf<Movie>()
    var moviesLiveData = MutableLiveData<List<Movie>>()
    var errorLiveData = MutableLiveData<String>()
    var stepLiveData = MutableLiveData<ProcessStep>()

    private fun getDataFromNetwork() {
        disposable.add(
            apiClient.callApi().subscribe({ movieResponse ->
                moviesLiveData.value = movieResponse.data
                fullMovieList.clear()
                fullMovieList.addAll(movieResponse.data)
                setApiDataToSharedPref(movieResponse)
                stepLiveData.value = ProcessStep.SUCCESS
            }, { error ->
                error.printStackTrace()
                errorLiveData.value = error.localizedMessage
                stepLiveData.value = ProcessStep.FAILURE
            })
        )
    }

    private fun setApiDataToSharedPref(movieResponse: MovieResponse) {
        pref.setMoviesData(movieResponse)
        pref.setTimerForCache()
    }

    fun getMoviesData() {
        stepLiveData.value = ProcessStep.PROCESSING
        if (pref.getExpiredTime() > System.currentTimeMillis()) {
            getDataFormSharedPref()
        } else {
            pref.clearPref()
            getDataFromNetwork()
        }

    }

    private fun getDataFormSharedPref() {
        pref.getSavedMoviesData()?.apply {
            moviesLiveData.value = this.data
            fullMovieList.clear()
            fullMovieList.addAll(this.data)
            stepLiveData.value = ProcessStep.SUCCESS
        }
    }

    fun filterMovies(keyword: String) {
        moviesLiveData.value =
            fullMovieList.filter {
                it.title.toLowerCase().contains(keyword.toLowerCase()) || it.genre.toLowerCase().contains(
                    keyword.toLowerCase()
                )
            }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}