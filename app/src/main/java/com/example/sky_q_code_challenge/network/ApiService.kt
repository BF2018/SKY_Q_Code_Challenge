package com.example.sky_q_code_challenge.network

import com.example.sky_q_code_challenge.data.Movies
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getMovies() : Observable<Movies>
}