package com.example.sky_q_code_challenge.network

import com.example.sky_q_code_challenge.data.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getMovies() : Single<MovieResponse>
}