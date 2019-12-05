package com.example.sky_q_code_challenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.sky_q_code_challenge.R
import com.example.sky_q_code_challenge.data.Movies
import com.example.sky_q_code_challenge.network.ApiClient
import com.example.sky_q_code_challenge.viewModel.MoviesViewModel

class MoviesActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    private var viewModel = MoviesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getDataFromNetwork()
        viewModel.getObservable().observe(this,
             Observer<Movies> {movies ->
                 fillRecyclerView(movies)})
    }

    private fun fillRecyclerView(movies: Movies){
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter  = MoviesAdapter(movies)
        val layoutManager = AutoFitGridLayoutManager(this, 500)
        recyclerView.layoutManager = layoutManager
    }


}
