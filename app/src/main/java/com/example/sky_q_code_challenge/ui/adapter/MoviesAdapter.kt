package com.example.sky_q_code_challenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sky_q_code_challenge.R
import com.example.sky_q_code_challenge.data.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*


class MoviesAdapter (private val movies: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = movies[position].title
        holder.genre.text = movies[position].genre
        holder.year.text = movies[position].year.toString()
        Picasso.get().load(movies[position].poster).into(holder.imageView)
    }



    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
       internal var title = item.title
       internal var genre = item.genre
       internal var year = item.year
       internal var imageView = item.imageView

    }

}