package com.example.sky_q_code_challenge.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.sky_q_code_challenge.R
import com.example.sky_q_code_challenge.data.Movies
import com.example.sky_q_code_challenge.ui.adapter.MoviesAdapter
import com.example.sky_q_code_challenge.viewModel.MoviesViewModel


class MoviesActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var adapter : MoviesAdapter
    private var viewModel = MoviesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getMoviesData()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val search = menu!!.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        search(searchView)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
           override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

           override fun onQueryTextChange(newText: String?): Boolean {
                if (adapter != null) adapter.getFilter()?.filter(newText)
                return true
            }
        })
    }



}
