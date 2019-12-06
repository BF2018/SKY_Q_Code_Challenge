package com.example.sky_q_code_challenge.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.sky_q_code_challenge.R
import com.example.sky_q_code_challenge.enums.ProcessStep
import com.example.sky_q_code_challenge.misc.Preferences
import com.example.sky_q_code_challenge.network.ApiClient
import com.example.sky_q_code_challenge.ui.adapter.MoviesAdapter
import com.example.sky_q_code_challenge.viewModel.MoviesViewModel
import com.example.sky_q_code_challenge.viewModel.MoviesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MoviesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = AutoFitGridLayoutManager(this, 500)
        val apiClient = ApiClient()
        val viewModelFactory = MoviesViewModelFactory(apiClient, Preferences(applicationContext))

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)

        viewModel.moviesLiveData.observe(this,
            Observer { movies ->
                recyclerView.adapter = MoviesAdapter(movies)
            })

        viewModel.stepLiveData.observe(this, Observer {
            resetContent()
            when (it) {
                ProcessStep.PROCESSING -> {
                    progress_circular.visibility = View.VISIBLE
                }
                ProcessStep.FAILURE -> {
                    tv_message.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE
                }
                ProcessStep.SUCCESS -> {
                    recyclerView.visibility = View.VISIBLE
                }
                else -> {
                    Toast.makeText(this, "Invalid Usecase", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.errorLiveData.observe(this, Observer {
            tv_message.text = it
        })
        viewModel.getMoviesData()

        btnSearch.setOnClickListener {
            viewModel.filterMovies(etQuery.text.toString())
        }
        btnRetry.setOnClickListener {
            viewModel.getMoviesData()
        }

    }

    private fun resetContent() {
        progress_circular.visibility = View.GONE
        tv_message.visibility = View.GONE
        recyclerView.visibility = View.GONE
        btnRetry.visibility = View.GONE
    }


}
