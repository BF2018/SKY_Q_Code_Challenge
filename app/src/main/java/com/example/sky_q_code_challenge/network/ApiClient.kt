package com.example.sky_q_code_challenge.network

import com.example.sky_q_code_challenge.data.MovieResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.sky_q_code_challenge.extensions.runApiCall


class ApiClient {

    fun callApi(): Single<MovieResponse> {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create<ApiService>(ApiService::class.java)
            .getMovies()
            .runApiCall()
    }


}